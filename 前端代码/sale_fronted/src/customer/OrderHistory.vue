<template>
  <div class="order-history">
    <h2>我的订单</h2>
    <div class="tabs">
      <button :class="{ active: tab === 'all' }" @click="tab = 'all'; loadOrders()">全部</button>
      <button :class="{ active: tab === 'unpaid' }" @click="tab = 'unpaid'; loadOrders()">未付款</button>
      <button :class="{ active: tab === 'unshipped' }" @click="tab = 'unshipped'; loadOrders()">未发货</button>
    </div>

    <div v-if="orders.length">
      <div class="order-card" v-for="o in orders" :key="o.orderId">
        <div class="order-header">
          <span class="order-id">订单号：{{ o.orderId }}</span>
          <span class="order-date">{{ o.orderDate }}</span>
          <span :class="'tag tag-' + o.supplyStatus">{{ statusLabel(o.supplyStatus) }}</span>
          <span :class="'tag tag-' + o.paymentStatus">{{ o.paymentStatus === 'paid' ? '已付款' : '未付款' }}</span>
        </div>
        <div class="order-body">
          <div class="detail-row" v-for="d in o.details" :key="d.detailId">
            <span>{{ d.productDescription || d.productId }}</span>
            <span>×{{ d.quantity }}</span>
            <span>¥{{ d.totalAmount }}</span>
          </div>
        </div>
        <div class="order-footer">
          <span>运费：¥{{ o.shippingFee || '0' }}</span>
          <span class="total">合计：<strong>¥{{ totalAmount(o) }}</strong></span>
        </div>
      </div>
    </div>
    <p v-else class="empty">暂无订单</p>
  </div>
</template>

<script>
import api from '@/api/index';
export default {
  name: 'OrderHistory',
  data() { return { orders: [], tab: 'all' }; },
  created() { this.loadOrders(); },
  methods: {
    async loadOrders() {
      try {
        const res = await api.getAllOrders();
        let filtered = res.data;
        if (this.tab === 'unpaid') filtered = filtered.filter(o => o.paymentStatus === 'unpaid');
        else if (this.tab === 'unshipped') filtered = filtered.filter(o => o.supplyStatus === 'pending');
        this.orders = await Promise.all(filtered.map(async o => {
          const detail = await api.getOrder(o.orderId);
          return detail.data;
        }));
      } catch (e) { console.error(e); }
    },
    totalAmount(o) {
      if (!o.details) return '0.00';
      return o.details.reduce((s, d) => s + d.totalAmount, 0).toFixed(2);
    },
    statusLabel(s) {
      return { pending: '待确认', available: '可供货', unavailable: '缺货' }[s] || s;
    }
  }
};
</script>

<style scoped>
.tabs {
  margin: 16px 0;
  display: flex;
  gap: 8px;
}

.tabs button {
  padding: 8px 20px;
  border: 1px solid #d9d9d9;
  background: #fff;
  border-radius: 4px;
  cursor: pointer;
}

.tabs button.active {
  background: #1890ff;
  color: #fff;
  border-color: #1890ff;
}

.order-card {
  background: #fff;
  border-radius: 8px;
  margin-bottom: 16px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .06);
}

.order-header {
  padding: 12px 20px;
  background: #fafafa;
  display: flex;
  align-items: center;
  gap: 16px;
  border-bottom: 1px solid #f0f0f0;
}

.order-id {
  font-weight: 600;
}

.order-date {
  color: #999;
  font-size: 13px;
}

.tag {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.tag-pending {
  background: #fff7e6;
  color: #d46b08;
}

.tag-available {
  background: #f6ffed;
  color: #52c41a;
}

.tag-unavailable {
  background: #fff2f0;
  color: #ff4d4f;
}

.tag-paid {
  background: #f6ffed;
  color: #52c41a;
}

.tag-unpaid {
  background: #fff7e6;
  color: #d46b08;
}

.order-body {
  padding: 12px 20px;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  padding: 6px 0;
  font-size: 14px;
  border-bottom: 1px dashed #f0f0f0;
}

.detail-row:last-child {
  border-bottom: none;
}

.order-footer {
  padding: 12px 20px;
  border-top: 1px solid #f0f0f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total strong {
  color: #ff4d4f;
  font-size: 18px;
}

.empty {
  text-align: center;
  color: #999;
  margin-top: 60px;
}
</style>
