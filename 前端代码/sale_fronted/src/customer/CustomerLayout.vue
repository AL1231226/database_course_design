<template>
  <div class="customer-layout">
    <header class="header">
      <div class="header-inner">
        <h1 class="site-title">🏪 体育用品批发商城</h1>
        <nav class="nav">
          <router-link to="/customer/products">商品列表</router-link>
          <router-link to="/customer/cart">购物车</router-link>
          <router-link to="/customer/orders">我的订单</router-link>
          <router-link to="/admin/dashboard" class="admin-link">后台管理</router-link>
        </nav>
      </div>
    </header>
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script>
export default { name: 'CustomerLayout' };
</script>

<style scoped>
.header { background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,.1); position: sticky; top: 0; z-index: 100; }
.header-inner { max-width: 1200px; margin: 0 auto; padding: 0 20px; height: 64px; display: flex; align-items: center; justify-content: space-between; }
.site-title { font-size: 20px; color: #1890ff; }
.nav { display: flex; gap: 24px; }
.nav a { color: #666; font-size: 15px; padding: 8px 0; border-bottom: 2px solid transparent; transition: all .3s; }
.nav a:hover, .nav a.router-link-active { color: #1890ff; border-bottom-color: #1890ff; }
.admin-link { background: #f6ffed; color: #52c41a !important; padding: 4px 12px !important; border-radius: 4px !important; border: 1px solid #b7eb8f !important; }
.content { max-width: 1200px; margin: 24px auto; padding: 0 20px; }
</style>
