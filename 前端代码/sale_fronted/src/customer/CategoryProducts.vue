<template>
  <div class="category-page">
    <div class="page-header">
      <button class="back-btn" @click="$router.push('/customer/products')">← 返回全部商品</button>
      <h2>{{ categoryLabel }}（{{ products.length }} 件）</h2>
    </div>

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
          </div>
          <div class="product-footer">
            <button class="btn" @click="addToCart(p)">加入购物车</button>
          </div>
        </div>
      </div>
      <div v-if="!products.length" class="empty">该分类暂无商品</div>
    </template>
  </div>
</template>

<script>
import api from '@/api/index';
const CATEGORY_MAP = {
  'SPORT-01': '球类运动',
  'SPORT-02': '羽毛球',
  'SPORT-03': '乒乓球',
  'SPORT-04': '健身器材',
  'SPORT-05': '游泳用品',
  'SPORT-06': '其他'
};
export default {
  name: 'CategoryProducts',
  data() {
    return { products: [], loading: true, error: '' };
  },
  computed: {
    categoryLabel() {
      return CATEGORY_MAP[this.$route.params.code] || this.$route.params.code;
    }
  },
  async created() {
    try {
      const res = await api.getProductsByCategory(this.$route.params.code);
      this.products = res.data;
    } catch (e) {
      this.error = '分类商品加载失败';
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
.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}
.page-header h2 {
  margin: 0;
  font-size: 18px;
}
.back-btn {
  padding: 6px 14px;
  border: 1px solid #d9d9d9;
  background: #fff;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
  color: #666;
  white-space: nowrap;
}
.back-btn:hover {
  color: #1890ff;
  border-color: #1890ff;
}
.products { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 20px; }
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
