<template>
  <div class="customer-layout">
    <header class="header">
      <div class="header-inner">
        <h1 class="site-title">🏪 体育用品批发商城</h1>
        <nav class="nav">
          <router-link to="/customer/products">商品列表</router-link>
          <router-link to="/customer/cart">购物车</router-link>
          <router-link to="/customer/orders">我的订单</router-link>
          <router-link v-if="user && user.role === 'admin'" to="/admin/dashboard" class="admin-link">后台管理</router-link>
        </nav>
        <div class="user-info" v-if="user">
          <span class="user-name">{{ user.name }}</span>
          <button class="logout-btn" v-if="user.role === 'customer'" @click="showPasswordDialog = true">修改密码</button>
          <button class="logout-btn" @click="handleLogout">退出</button>
        </div>
        <router-link v-else to="/login" class="login-link">登录</router-link>
      </div>
    </header>

    <!-- 修改密码弹窗 -->
    <div class="modal-overlay" v-if="showPasswordDialog" @click.self="showPasswordDialog = false">
      <div class="modal">
        <div class="modal-header">
          <h3>修改密码</h3>
          <span class="close" @click="showPasswordDialog = false">&times;</span>
        </div>
        <div class="modal-body">
          <div class="form-item">
            <label>原密码</label>
            <input type="password" v-model="passwordForm.oldPassword" placeholder="输入原密码">
          </div>
          <div class="form-item">
            <label>新密码</label>
            <input type="password" v-model="passwordForm.newPassword" placeholder="至少6位">
          </div>
          <div class="form-item">
            <label>确认新密码</label>
            <input type="password" v-model="passwordForm.confirmPassword" placeholder="再次输入新密码">
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" :disabled="changingPassword" @click="handleChangePassword">
            {{ changingPassword ? '修改中...' : '确认修改' }}
          </button>
          <button class="btn btn-cancel" @click="showPasswordDialog = false">取消</button>
        </div>
      </div>
    </div>

    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script>
import api from '@/api/index';
export default {
  name: 'CustomerLayout',
  data() {
    return {
      user: null,
      showPasswordDialog: false,
      passwordForm: { oldPassword: '', newPassword: '', confirmPassword: '' },
      changingPassword: false,
    };
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
    },
    async handleChangePassword() {
      if (!this.passwordForm.oldPassword || !this.passwordForm.newPassword) {
        return alert('请填写完整');
      }
      if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
        return alert('两次新密码不一致');
      }
      if (this.passwordForm.newPassword.length < 6) {
        return alert('新密码至少6位');
      }
      this.changingPassword = true;
      try {
        await api.changePassword({
          oldPassword: this.passwordForm.oldPassword,
          newPassword: this.passwordForm.newPassword,
        });
        alert('密码修改成功');
        this.showPasswordDialog = false;
        this.passwordForm = { oldPassword: '', newPassword: '', confirmPassword: '' };
      } catch (e) {
        alert(e.response?.data?.error || '修改失败');
      } finally {
        this.changingPassword = false;
      }
    }
  }
};
</script>

<style scoped>
.header { background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,.1); position: sticky; top: 0; z-index: 100; }
.header-inner { max-width: 1200px; margin: 0 auto; padding: 0 20px; height: 64px; display: flex; align-items: center; justify-content: space-between; }
.site-title { font-size: 20px; color: #1890ff; }
.nav { display: flex; gap: 24px; }
.nav a { color: #666; font-size: 15px; padding: 8px 0; border-bottom: 2px solid transparent; transition: all .3s; }
.nav a:hover, .nav a.router-link-active { color: #1890ff; border-bottom-color: #1890ff; }
.admin-link { background: #f6ffed; color: #52c41a !important; padding: 4px 12px !important; border-radius: 4px !important; border: 1px solid #b7eb8f !important; }
.user-info { display: flex; align-items: center; gap: 12px; }
.user-name { font-size: 14px; color: #333; font-weight: 500; }
.logout-btn { padding: 4px 12px; border: 1px solid #d9d9d9; border-radius: 4px; background: #fff; cursor: pointer; font-size: 13px; color: #666; }
.logout-btn:hover { color: #ff4d4f; border-color: #ff4d4f; }
.login-link { color: #1890ff; font-size: 14px; text-decoration: none; }
.login-link:hover { text-decoration: underline; }
.content { max-width: 1200px; margin: 24px auto; padding: 0 20px; }

/* Modal */
.modal-overlay { position: fixed; top:0;left:0;right:0;bottom:0; background:rgba(0,0,0,.45); display:flex; align-items:center; justify-content:center; z-index:1000; }
.modal { background:#fff; border-radius:8px; width:400px; box-shadow:0 8px 24px rgba(0,0,0,.15); }
.modal-header { display:flex; justify-content:space-between; align-items:center; padding:20px 24px 0; }
.modal-header h3 { margin:0; font-size:18px; }
.close { font-size:28px; cursor:pointer; color:#999; line-height:1; }
.close:hover { color:#333; }
.modal-body { padding:20px 24px; }
.modal-footer { padding:16px 24px; border-top:1px solid #f0f0f0; display:flex; gap:12px; justify-content:flex-end; }
.form-item { margin-bottom:16px; }
.form-item label { display:block; margin-bottom:4px; font-weight:500; font-size:14px; }
.form-item input { width:100%; padding:8px 12px; border:1px solid #d9d9d9; border-radius:4px; font-size:14px; box-sizing:border-box; }
.form-item input:focus { outline:none; border-color:#1890ff; box-shadow:0 0 0 2px rgba(24,144,255,.2); }
.btn { padding:8px 20px; border:none; border-radius:4px; cursor:pointer; font-size:14px; }
.btn-primary { background:#1890ff; color:#fff; }
.btn-primary:disabled { opacity:.6; cursor:not-allowed; }
.btn-cancel { background:#f5f5f5; color:#666; }
</style>
