<template>
  <div class="cart">
    <div class="cart-header">
      <h2>购物车</h2>
      <router-link to="/customer/products" class="continue-shopping">← 继续选购</router-link>
    </div>

    <!-- 加载中 -->
    <div v-if="loading" class="loading">加载中...</div>

    <!-- 错误提示 -->
    <div v-else-if="error" class="error">{{ error }}</div>

    <!-- 购物车有商品 -->
    <template v-else-if="cart.length">
      <table class="table">
        <thead>
          <tr>
            <th>商品信息</th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, i) in cart" :key="item.productId">
            <td class="info-cell">
              <div class="info">
                <div class="name">{{ item.description }}</div>
                <div class="meta">厂家：{{ item.manufacturerName || '未知' }}</div>
                <div class="meta" v-if="item.stockQuantity !== undefined">库存：{{ item.stockQuantity }} 件</div>
              </div>
            </td>
            <td>¥{{ item.unitPrice }}</td>
            <td class="qty-cell">
              <button class="qty-btn" @click="changeQty(i, -1)" :disabled="item.quantity <= 1">-</button>
              <input class="qty-input" type="number" :value="item.quantity"
                     @input="onQtyInput(i, $event)" min="1" />
              <button class="qty-btn" @click="changeQty(i, 1)">+</button>
            </td>
            <td class="subtotal">¥{{ (item.unitPrice * item.quantity).toFixed(2) }}</td>
            <td>
              <button class="btn btn-danger btn-sm" @click="removeItem(i)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="cart-footer">
        <span class="total">
          合计（{{ itemCount }} 件）：
          <strong>¥{{ total.toFixed(2) }}</strong>
        </span>
        <button class="btn btn-primary btn-lg" @click="showCheckout = true">去结算</button>
      </div>
    </template>

    <!-- 购物车为空 -->
    <div v-else class="empty-cart">
      <div class="empty-icon">🛒</div>
      <p class="empty-text">购物车是空的</p>
      <router-link to="/customer/products" class="btn btn-primary">去选购商品</router-link>
    </div>

    <!-- 结算弹窗 -->
    <div class="modal-overlay" v-if="showCheckout" @click.self="showCheckout=false">
      <div class="modal">
        <div class="modal-header">
          <h3>订单结算</h3>
          <span class="close" @click="showCheckout=false">&times;</span>
        </div>
        <div class="modal-body">
          <div class="form-item" :class="{ invalid: !form.customerId && submitted }">
            <label>顾客 <span class="required">*</span></label>
            <select v-model="form.customerId">
              <option value="" disabled>请选择顾客</option>
              <option v-for="c in customers" :key="c.customerId" :value="c.customerId">
                {{ c.customerName }} ({{ c.customerId }})
              </option>
            </select>
            <span class="error-msg" v-if="!form.customerId && submitted">请选择顾客</span>
          </div>
          <div class="form-item">
            <label>运输要求</label>
            <input v-model="form.shippingRequirements" placeholder="如：标准运输，轻拿轻放" maxlength="200" />
          </div>
          <div class="form-item">
            <label>付款方式 <span class="required">*</span></label>
            <select v-model="form.paymentInfo">
              <option value="微信支付">微信支付</option>
              <option value="支付宝">支付宝</option>
              <option value="银行转账">银行转账</option>
            </select>
          </div>
          <div class="order-summary">
            <h4>订单汇总</h4>
            <div class="summary-row" v-for="item in cart" :key="item.productId">
              <span>{{ item.description }} × {{ item.quantity }}</span>
              <span>¥{{ (item.unitPrice * item.quantity).toFixed(2) }}</span>
            </div>
            <div class="summary-total">
              <span>合计</span>
              <span>¥{{ total.toFixed(2) }}</span>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" :disabled="submitting" @click="submitOrder">
            <span v-if="submitting" class="spinner"></span>
            {{ submitting ? '提交中...' : '提交订单' }}
          </button>
          <button class="btn btn-cancel" @click="showCheckout=false">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCustomers, createOrder } from '@/api/index';

export default {
  name: 'ShoppingCart',
  data() {
    return {
      cart: [],
      customers: [],
      loading: true,
      error: '',
      showCheckout: false,
      submitting: false,
      submitted: false,
      form: { customerId: '', shippingRequirements: '', paymentInfo: '微信支付' }
    };
  },
  computed: {
    total() { return this.cart.reduce((s, i) => s + i.unitPrice * i.quantity, 0); },
    itemCount() { return this.cart.reduce((s, i) => s + i.quantity, 0); }
  },
  created() {
    this.loadCart();
    this.loadCustomers();
  },
  methods: {
    loadCart() {
      this.cart = JSON.parse(sessionStorage.getItem('cart') || '[]');
      this.loading = false;
    },
    async loadCustomers() {
      try {
        const res = await getCustomers();
        this.customers = res.data;
      } catch (e) {
        this.error = '顾客信息加载失败，无法结算';
      }
    },
    changeQty(i, delta) {
      this.cart[i].quantity = Math.max(1, (this.cart[i].quantity || 1) + delta);
      this.saveCart();
    },
    onQtyInput(i, event) {
      const val = parseInt(event.target.value, 10);
      if (!isNaN(val) && val > 0) {
        this.cart[i].quantity = val;
        this.saveCart();
      }
    },
    removeItem(i) {
      this.cart.splice(i, 1);
      this.saveCart();
    },
    saveCart() {
      sessionStorage.setItem('cart', JSON.stringify(this.cart));
    },
    async submitOrder() {
      this.submitted = true;
      if (!this.form.customerId) return;

      this.submitting = true;
      try {
        const orderId = 'ORD' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + String(Date.now()).slice(-4);
        const order = {
          orderId,
          orderDate: new Date().toISOString().slice(0, 10),
          customerId: this.form.customerId,
          shippingRequirements: this.form.shippingRequirements,
          supplyStatus: 'pending',
          paymentInfo: this.form.paymentInfo,
          paymentStatus: 'unpaid',
          details: this.cart.map(item => ({
            productId: item.productId,
            manufacturerCode: item.manufacturerCode,
            quantity: item.quantity,
            unitPrice: item.unitPrice,
            totalAmount: item.unitPrice * item.quantity
          }))
        };
        await createOrder(order);
        this.showCheckout = false;
        this.submitted = false;
        sessionStorage.removeItem('cart');
        this.cart = [];
        this.$router.push('/customer/orders');
      } catch (e) {
        alert('订单提交失败，请稍后重试');
      } finally {
        this.submitting = false;
      }
    }
  }
};
</script>

<style scoped>
/* Header */
.cart-header { display: flex; justify-content: space-between; align-items: center; }
.cart-header h2 { margin: 0; }
.continue-shopping { color: #1890ff; font-size: 14px; text-decoration: none; }
.continue-shopping:hover { text-decoration: underline; }

/* Table */
.table { width: 100%; background: #fff; border-radius: 8px; overflow: hidden; margin-top: 16px; border-collapse: collapse; }
.table th, .table td { padding: 14px 16px; text-align: left; border-bottom: 1px solid #f0f0f0; }
.table th { background: #fafafa; font-weight: 600; font-size: 13px; color: #666; }
.table th:last-child, .table td:last-child { text-align: center; }

/* Info cell */
.info-cell { min-width: 200px; }
.info .name { font-weight: 500; font-size: 15px; margin-bottom: 4px; }
.info .meta { font-size: 12px; color: #999; }

/* Quantity */
.qty-cell { white-space: nowrap; }
.qty-btn { width: 28px; height: 28px; border: 1px solid #d9d9d9; background: #fff; border-radius: 4px; cursor: pointer; font-size: 16px; line-height: 1; vertical-align: middle; }
.qty-btn:hover:not(:disabled) { border-color: #1890ff; color: #1890ff; }
.qty-btn:disabled { opacity: 0.4; cursor: not-allowed; }
.qty-input { width: 48px; height: 28px; margin: 0 4px; text-align: center; border: 1px solid #d9d9d9; border-radius: 4px; font-size: 14px; vertical-align: middle; }
.qty-input::-webkit-inner-spin-button, .qty-input::-webkit-outer-spin-button { -webkit-appearance: none; margin: 0; }
.subtotal { font-weight: 500; color: #ff4d4f; }

/* Cart footer */
.cart-footer { margin-top: 16px; display: flex; justify-content: space-between; align-items: center; padding: 16px 24px; background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,.06); }
.total { font-size: 16px; }
.total strong { font-size: 22px; color: #ff4d4f; margin-left: 4px; }

/* Buttons */
.btn { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; display: inline-flex; align-items: center; gap: 6px; }
.btn-primary { background: #1890ff; color: #fff; }
.btn-primary:hover:not(:disabled) { background: #40a9ff; }
.btn-primary:disabled { opacity: 0.6; cursor: not-allowed; }
.btn-danger { background: #ff4d4f; color: #fff; }
.btn-danger:hover { background: #ff7875; }
.btn-cancel { background: #f5f5f5; color: #666; }
.btn-cancel:hover { background: #e8e8e8; }
.btn-sm { padding: 4px 12px; font-size: 12px; }
.btn-lg { padding: 10px 32px; font-size: 16px; }

/* Loading / Error / Empty */
.loading, .error { text-align: center; margin-top: 60px; font-size: 16px; }
.loading { color: #999; }
.error { color: #ff4d4f; }
.empty-cart { text-align: center; margin-top: 80px; }
.empty-icon { font-size: 64px; margin-bottom: 16px; }
.empty-text { font-size: 18px; color: #999; margin-bottom: 24px; }

/* Spinner */
.spinner { display: inline-block; width: 14px; height: 14px; border: 2px solid rgba(255,255,255,.3); border-top-color: #fff; border-radius: 50%; animation: spin .6s linear infinite; }
@keyframes spin { to { transform: rotate(360deg); } }

/* Modal overlay */
.modal-overlay { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,.45); display: flex; align-items: center; justify-content: center; z-index: 1000; }
.modal { background: #fff; border-radius: 8px; width: 500px; max-height: 80vh; display: flex; flex-direction: column; box-shadow: 0 8px 24px rgba(0,0,0,.15); }
.modal-header { display: flex; justify-content: space-between; align-items: center; padding: 20px 24px 0; }
.modal-header h3 { margin: 0; font-size: 18px; }
.close { font-size: 28px; cursor: pointer; color: #999; line-height: 1; }
.close:hover { color: #333; }
.modal-body { padding: 20px 24px; overflow-y: auto; }
.modal-footer { padding: 16px 24px; border-top: 1px solid #f0f0f0; display: flex; gap: 12px; justify-content: flex-end; }

/* Form */
.form-item { margin-bottom: 16px; }
.form-item label { display: block; margin-bottom: 4px; font-weight: 500; font-size: 14px; }
.required { color: #ff4d4f; }
.form-item input, .form-item select { width: 100%; padding: 8px 12px; border: 1px solid #d9d9d9; border-radius: 4px; font-size: 14px; box-sizing: border-box; }
.form-item input:focus, .form-item select:focus { outline: none; border-color: #1890ff; box-shadow: 0 0 0 2px rgba(24,144,255,.2); }
.form-item.invalid input, .form-item.invalid select { border-color: #ff4d4f; }
.error-msg { color: #ff4d4f; font-size: 12px; margin-top: 4px; display: block; }

/* Order summary */
.order-summary { background: #fafafa; border-radius: 6px; padding: 16px; margin-top: 8px; }
.order-summary h4 { margin: 0 0 12px; font-size: 14px; color: #333; }
.summary-row { display: flex; justify-content: space-between; font-size: 13px; color: #666; margin-bottom: 6px; }
.summary-total { display: flex; justify-content: space-between; font-size: 15px; font-weight: 600; color: #333; border-top: 1px solid #e8e8e8; padding-top: 10px; margin-top: 8px; }
.summary-total span:last-child { color: #ff4d4f; font-size: 18px; }

/* Responsive */
@media (max-width: 768px) {
  .table { font-size: 13px; }
  .table th, .table td { padding: 10px 8px; }
  .info-cell { min-width: auto; }
  .qty-input { width: 36px; }
  .modal { width: 92%; min-width: 0; }
}
</style>
