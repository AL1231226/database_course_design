<template>
  <div class="admin-layout">
    <aside class="sidebar">
      <h2 class="logo">⚙️ 后台管理</h2>
      <div class="admin-info" v-if="user">
        <span class="admin-name">{{ user.name }}</span>
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </div>
      <nav>
        <router-link to="/admin/dashboard">📊 控制面板</router-link>
        <router-link to="/admin/orders">📦 订单管理</router-link>
        <router-link to="/admin/inventory">📋 库存管理</router-link>
        <router-link to="/admin/customers">👥 顾客管理</router-link>
        <router-link to="/admin/manufacturers">🏭 厂家管理</router-link>
        <router-link to="/customer/products">🛒 前台商城</router-link>
      </nav>
    </aside>
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'AdminLayout',
  data() {
    return { user: null };
  },
  created() {
    this.loadUser();
  },
  methods: {
    loadUser() {
      const raw = sessionStorage.getItem('user');
      this.user = raw ? JSON.parse(raw) : null;
    },
    handleLogout() {
      sessionStorage.removeItem('user');
      this.user = null;
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
.admin-layout { display: flex; min-height: 100vh; }
.sidebar {
  width: 240px; background: #001529; color: #fff; padding: 20px 0; flex-shrink: 0;
}
.logo { padding: 0 20px 20px; font-size: 18px; border-bottom: 1px solid rgba(255,255,255,.1); margin-bottom: 10px; }
.admin-info { padding: 12px 20px; border-bottom: 1px solid rgba(255,255,255,.1); margin-bottom: 10px; display: flex; align-items: center; gap: 8px; }
.admin-name { font-size: 14px; color: #fff; flex: 1; }
.logout-btn { padding: 2px 10px; border: 1px solid rgba(255,255,255,.3); border-radius: 4px; background: transparent; cursor: pointer; font-size: 12px; color: rgba(255,255,255,.65); }
.logout-btn:hover { color: #ff4d4f; border-color: #ff4d4f; }
.sidebar nav a {
  display: block; padding: 14px 24px; color: rgba(255,255,255,.65); transition: all .3s; font-size: 15px;
}
.sidebar nav a:hover, .sidebar nav a.router-link-active { color: #fff; background: #1890ff; }
.main-content { flex: 1; padding: 24px; background: #f0f2f5; overflow-y: auto; }
</style>
