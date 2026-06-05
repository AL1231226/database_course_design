<template>
  <div class="dashboard">
    <h2>控制面板</h2>
    <div class="stats" v-if="stats">
      <div class="card">
        <h3>📦 总订单</h3>
        <p>{{ stats.totalOrders }}</p>
      </div>
      <div class="card warning">
        <h3>⏳ 未付款</h3>
        <p>{{ stats.unpaid }}</p>
      </div>
      <div class="card danger">
        <h3>🚫 未发货</h3>
        <p>{{ stats.unshipped }}</p>
      </div>
      <div class="card success">
        <h3>✅ 已完成</h3>
        <p>{{ stats.completed }}</p>
      </div>
      <div class="card info">
        <h3>🏭 厂家数</h3>
        <p>{{ stats.manufacturers }}</p>
      </div>
      <div class="card info">
        <h3>👥 顾客数</h3>
        <p>{{ stats.customers }}</p>
      </div>
    </div>

    <h3 style="margin-top:30px">⚠️ 库存不足提醒</h3>
    <table class="table" v-if="lowStock.length">
      <thead>
        <tr>
          <th>编号</th>
          <th>商品名</th>
          <th>库存</th>
          <th>厂家</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in lowStock" :key="p.productId">
          <td>{{ p.productId }}</td>
          <td>{{ p.description }}</td>
          <td style="color:#ff4d4f;font-weight:bold">{{ p.stockQuantity }}</td>
          <td>{{ p.manufacturerName }}</td>
          <td><button class="btn btn-sm" @click="contactManufacturer(p)">联系进货</button></td>
        </tr>
      </tbody>
    </table>
    <p v-else class="empty">暂无库存不足商品</p>

    <div v-if="contactMsg" class="alert">{{ contactMsg }}</div>
  </div>
</template>

<script>
import api from '@/api/index';
export default {
  name: 'AdminDashboard',
  data() {
    return { stats: null, lowStock: [], contactMsg: '' };
  },
  async created() {
    try {
      const [orders, unpaid, unshipped, completed, manufacturers, customers, low] = await Promise.all([
        api.getOrders(), api.getUnpaidOrders(), api.getUnshippedOrders(),
        api.getCompletedOrders(), api.getManufacturers(), api.getCustomers(),
        api.getLowStock(50)
      ]);
      this.stats = {
        totalOrders: orders.data.length, unpaid: unpaid.data.length,
        unshipped: unshipped.data.length, completed: completed.data.length,
        manufacturers: manufacturers.data.length, customers: customers.data.length
      };
      this.lowStock = low.data.products;
    } catch (e) { console.error('加载数据失败', e); }
  },
  methods: {
    contactManufacturer(p) {
      this.contactMsg = `已通知厂家「${p.manufacturerName}」补充商品「${p.description}」，当前库存：${p.stockQuantity}`;
      setTimeout(() => this.contactMsg = '', 5000);
    }
  }
};
</script>

<style scoped>
.stats {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
  margin-top: 20px;
}

.card {
  background: #fff;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .06);
}

.card h3 {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.card p {
  font-size: 32px;
  font-weight: bold;
  color: #333;
}

.card.warning p {
  color: #faad14;
}

.card.danger p {
  color: #ff4d4f;
}

.card.success p {
  color: #52c41a;
}

.card.info p {
  color: #1890ff;
}

.table {
  width: 100%;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  margin-top: 12px;
}

.table th,
.table td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

.table th {
  background: #fafafa;
  font-weight: 600;
}

.btn {
  padding: 6px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
}

.btn-sm {
  padding: 4px 12px;
  background: #1890ff;
  color: #fff;
}

.alert {
  margin-top: 16px;
  padding: 12px 16px;
  background: #f6ffed;
  border: 1px solid #b7eb8f;
  border-radius: 4px;
  color: #52c41a;
}

.empty {
  color: #999;
  margin-top: 12px;
}
</style>
