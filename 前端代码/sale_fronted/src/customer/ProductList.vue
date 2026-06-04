<template>
  <div class="product-list">
    <h2>商品列表</h2>

    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <template v-else>
      <div class="products">
        <div class="product-card" v-for="p in products" :key="p.productId">
          <div class="product-body">
            <h3>{{ p.description }}</h3>
            <p class="price">¥{{ p.unitPrice }}</p>
            <p class="meta">厂家：{{ p.manufacturerName }}</p>
            <p class="meta">库存：{{ p.stockQuantity }} 件</p>
            <p class="meta">分类：{{ p.categoryCode }}</p>
          </div>
          <div class="product-footer">
            <button class="btn" @click="addToCart(p)">加入购物车</button>
          </div>
        </div>
      </div>
      <div v-if="!products.length" class="empty">暂无商品</div>
    </template>
  </div>
</template>

<script>
import { getProducts } from '@/api/index';
export default {
  name: 'ProductList',
  data() {
    return { products: [], loading: true, error: '' };
  },
  async created() {
    try {
      this.products = (await getProducts()).data;
    } catch (e) {
      this.error = '商品加载失败，请稍后重试';
    } finally {
      this.loading = false;
    }
  },
  methods: {
    addToCart(p) {
      let cart = JSON.parse(sessionStorage.getItem('cart') || '[]');
      const exist = cart.find(item => item.productId === p.productId);
      if (exist) {
        exist.quantity += 1;
      } else {
        cart.push({ ...p, quantity: 1 });
      }
      sessionStorage.setItem('cart', JSON.stringify(cart));
      alert(`「${p.description}」已加入购物车`);
    }
  }
};
</script>

<style scoped>
.products { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 20px; margin-top: 20px; }
.product-card { background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,.06); transition: all .3s; display: flex; flex-direction: column; }
.product-card:hover { transform: translateY(-4px); box-shadow: 0 4px 16px rgba(0,0,0,.12); }
.product-body { padding: 20px; flex: 1; }
.product-body h3 { font-size: 16px; margin-bottom: 12px; }
.price { font-size: 24px; color: #ff4d4f; font-weight: bold; margin-bottom: 12px; }
.meta { color: #999; font-size: 13px; margin-bottom: 4px; }
.product-footer { padding: 12px 20px; border-top: 1px solid #f0f0f0; }
.btn { width: 100%; padding: 8px; background: #1890ff; color: #fff; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; }
.btn:hover { background: #40a9ff; }
.empty { text-align: center; color: #999; margin-top: 60px; font-size: 16px; }
.loading { text-align: center; color: #999; margin-top: 60px; font-size: 16px; }
.error { text-align: center; color: #ff4d4f; margin-top: 60px; font-size: 16px; }
</style>
