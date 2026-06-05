<template>
  <div class="order-manage">
    <h2>订单管理</h2>
    <div class="tabs">
      <button :class="{active: tab === 'all'}" @click="tab='all';loadOrders()">全部</button>
      <button :class="{active: tab === 'unpaid'}" @click="tab='unpaid';loadOrders()">未付款</button>
      <button :class="{active: tab === 'unshipped'}" @click="tab='unshipped';loadOrders()">未发货</button>
      <button :class="{active: tab === 'completed'}" @click="tab='completed';loadOrders()">已完成</button>
    </div>
    <table class="table">
      <thead><tr>
        <th>订单号</th><th>顾客</th><th>日期</th><th>总金额</th><th>供货状态</th><th>付款状态</th><th>操作</th>
      </tr></thead>
      <tbody>
        <tr v-for="o in orders" :key="o.orderId">
          <td>{{ o.orderId }}</td>
          <td>{{ o.customerName }}</td>
          <td>{{ o.orderDate }}</td>
          <td>{{ totalAmount(o) }} 元</td>
          <td><span :class="'tag tag-'+o.supplyStatus">{{ statusLabel(o.supplyStatus) }}</span></td>
          <td><span :class="'tag tag-'+o.paymentStatus">{{ o.paymentStatus === 'paid' ? '已付款' : '未付款' }}</span></td>
          <td class="actions">
            <button class="btn btn-info" @click="viewDetail(o)">详情</button>
            <button class="btn btn-success" v-if="o.supplyStatus==='pending'" @click="handleConfirm(o)">确认供货</button>
            <button class="btn btn-warning" v-if="o.supplyStatus==='available' && o.paymentStatus==='unpaid'" @click="handlePay(o)">标记付款</button>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-if="!orders.length" class="empty">暂无数据</p>

    <!-- 详情弹窗 -->
    <div class="modal" v-if="detail">
      <div class="modal-content">
        <span class="close" @click="detail=null">&times;</span>
        <h3>订单详情 - {{ detail.orderId }}</h3>
        <p><strong>顾客：</strong>{{ detail.customerName }}</p>
        <p><strong>下单日期：</strong>{{ detail.orderDate }}</p>
        <p><strong>运输要求：</strong>{{ detail.shippingRequirements || '无' }}</p>
        <p><strong>供货状态：</strong>{{ statusLabel(detail.supplyStatus) }}</p>
        <p><strong>付款状态：</strong>{{ detail.paymentStatus === 'paid' ? '已付款' : '未付款' }}</p>
        <p><strong>货物重量：</strong>{{ detail.weight || '-' }} kg</p>
        <p><strong>运费：</strong>{{ detail.shippingFee || '-' }} 元</p>
        <h4 style="margin-top:16px">商品明细</h4>
        <table class="table">
          <thead><tr><th>商品</th><th>数量</th><th>单价</th><th>小计</th></tr></thead>
          <tbody>
            <tr v-for="d in detail.details" :key="d.detailId">
              <td>{{ d.productDescription || d.productId }}</td>
              <td>{{ d.quantity }}</td>
              <td>{{ d.unitPrice }} 元</td>
              <td>{{ d.totalAmount }} 元</td>
            </tr>
          </tbody>
        </table>
        <p style="margin-top:12px;text-align:right;font-size:18px;font-weight:bold">合计：{{ totalAmount(detail) }} 元</p>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../../api';
export default {
  name: 'OrderManage',
  data() { return { orders: [], tab: 'all', detail: null }; },
  created() { this.loadOrders(); },
  methods: {
    async loadOrders() {
      try {
        const map = { all: 'getOrders', unpaid: 'getUnpaidOrders', unshipped: 'getUnshippedOrders', completed: 'getCompletedOrders' };
        const res = await api[map[this.tab]]();
        this.orders = res.data;
      } catch (e) { console.error(e); }
    },
    async handleConfirm(o) {
      await api.confirmSupply(o.orderId);
      this.loadOrders();
    },
    async handlePay(o) {
      await api.updatePayment(o.orderId, { paymentStatus: 'paid', paymentInfo: '管理员手动确认' });
      this.loadOrders();
    },
    async viewDetail(o) {
      const res = await api.getOrder(o.orderId);
      this.detail = res.data;
    },
    totalAmount(o) {
      if (!o.details) return '-';
      return o.details.reduce((s, d) => s + d.totalAmount, 0);
    },
    statusLabel(s) {
      return { pending: '待确认', available: '可供货', unavailable: '缺货' }[s] || s;
    }
  }
};
</script>

<style scoped>
.tabs { margin: 16px 0; display: flex; gap: 8px; }
.tabs button { padding: 8px 20px; border: 1px solid #d9d9d9; background: #fff; border-radius: 4px; cursor: pointer; }
.tabs button.active { background: #1890ff; color: #fff; border-color: #1890ff; }
.table { width: 100%; background: #fff; border-radius: 8px; overflow: hidden; }
.table th, .table td { padding: 12px 16px; text-align: left; border-bottom: 1px solid #f0f0f0; }
.table th { background: #fafafa; font-weight: 600; }
.tag { display: inline-block; padding: 2px 8px; border-radius: 4px; font-size: 12px; }
.tag-pending { background: #fff7e6; color: #d46b08; }
.tag-available { background: #f6ffed; color: #52c41a; }
.tag-unavailable { background: #fff2f0; color: #ff4d4f; }
.tag-paid { background: #f6ffed; color: #52c41a; }
.tag-unpaid { background: #fff7e6; color: #d46b08; }
.actions { display: flex; gap: 6px; }
.btn { padding: 4px 12px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; }
.btn-info { background: #1890ff; color: #fff; }
.btn-success { background: #52c41a; color: #fff; }
.btn-warning { background: #faad14; color: #fff; }
.empty { color: #999; margin-top: 16px; text-align: center; }
.modal { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,.5); display: flex; align-items: center; justify-content: center; z-index: 1000; }
.modal-content { background: #fff; border-radius: 8px; padding: 24px; min-width: 600px; max-height: 80vh; overflow-y: auto; position: relative; }
.close { position: absolute; top: 16px; right: 20px; font-size: 28px; cursor: pointer; color: #999; }
</style>
