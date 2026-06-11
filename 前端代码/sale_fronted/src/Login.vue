<template>
  <div class="login-page">
    <div class="login-card">
      <h1 class="title">体育用品批发商城</h1>
      <div class="tabs">
        <button :class="['tab', { active: tab === 'customer' }]" @click="tab = 'customer'">顾客登录</button>
        <button :class="['tab', { active: tab === 'admin' }]" @click="tab = 'admin'">管理员登录</button>
      </div>

      <!-- 登录表单 -->
      <form @submit.prevent="handleLogin" v-if="!showRegister">
        <div class="field">
          <label>{{ tab === 'customer' ? '顾客代码' : '用户名' }}</label>
          <input v-model="form.account" :placeholder="tab === 'customer' ? '输入顾客代码或手机号' : '输入管理员用户名'" />
        </div>
        <div class="field">
          <label>密码</label>
          <input v-model="form.password" type="password" placeholder="输入密码" />
        </div>
        <p v-if="error" class="error">{{ error }}</p>
        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>

      <!-- 注册表单 -->
      <form @submit.prevent="handleRegister" v-else>
        <div class="field">
          <label>顾客名称 <span class="required">*</span></label>
          <input v-model="regForm.customerName" placeholder="公司/商店名称" required />
        </div>
        <div class="field">
          <label>联系人姓名 <span class="required">*</span></label>
          <input v-model="regForm.contactName" placeholder="联系人姓名" required />
        </div>
        <div class="field">
          <label>联系电话</label>
          <input v-model="regForm.phone" placeholder="联系电话" />
        </div>
        <div class="field">
          <label>地址</label>
          <input v-model="regForm.address" placeholder="地址" />
        </div>
        <div class="field">
          <label>邮编</label>
          <input v-model="regForm.zipCode" placeholder="邮编" />
        </div>
        <div class="field">
          <label>密码 <span class="required">*</span></label>
          <input v-model="regForm.password" type="password" placeholder="设置密码" required />
        </div>
        <p v-if="error" class="error">{{ error }}</p>
        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? '注册中...' : '注册并登录' }}
        </button>
      </form>

      <p class="switch-text">
        <template v-if="!showRegister">
          没有账号？
          <a href="#" @click.prevent="showRegister = true; error = ''">注册新顾客</a>
        </template>
        <template v-else>
          已有账号？
          <a href="#" @click.prevent="showRegister = false; error = ''">去登录</a>
        </template>
      </p>
    </div>
  </div>
</template>

<script>
import api from '@/api/index';

export default {
  name: 'Login',
  data() {
    return {
      tab: 'customer',
      showRegister: false,
      loading: false,
      error: '',
      form: { account: '', password: '' },
      regForm: { customerName: '', contactName: '', phone: '', address: '', zipCode: '', password: '' }
    };
  },
  methods: {
    async handleLogin() {
      if (!this.form.account || !this.form.password) {
        this.error = '请填写账号和密码';
        return;
      }
      this.loading = true;
      this.error = '';
      try {
        const res = await api.login({
          account: this.form.account,
          password: this.form.password,
          role: this.tab
        });
        sessionStorage.setItem('user', JSON.stringify(res.data));
        const redirect = this.$route.query.redirect || (this.tab === 'admin' ? '/admin' : '/customer');
        this.$router.push(redirect);
      } catch (e) {
        this.error = e.response?.data?.error || '登录失败';
      } finally {
        this.loading = false;
      }
    },
    async handleRegister() {
      if (!this.regForm.customerName || !this.regForm.contactName || !this.regForm.password) {
        this.error = '请填写必填项';
        return;
      }
      this.loading = true;
      this.error = '';
      try {
        const res = await api.register({
          customerName: this.regForm.customerName,
          contactName: this.regForm.contactName,
          phone: this.regForm.phone,
          address: this.regForm.address,
          zipCode: this.regForm.zipCode,
          password: this.regForm.password
        });
        sessionStorage.setItem('user', JSON.stringify(res.data));
        this.$router.push('/customer');
      } catch (e) {
        this.error = e.response?.data?.error || '注册失败';
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  background: #fff;
  border-radius: 12px;
  padding: 40px;
  width: 400px;
  max-width: 90vw;
  box-shadow: 0 8px 40px rgba(0, 0, 0, .15);
}

.title {
  text-align: center;
  font-size: 24px;
  color: #1890ff;
  margin: 0 0 24px;
}

.tabs {
  display: flex;
  border-bottom: 2px solid #f0f0f0;
  margin-bottom: 24px;
}

.tab {
  flex: 1;
  padding: 10px;
  border: none;
  background: none;
  font-size: 15px;
  cursor: pointer;
  color: #999;
  border-bottom: 2px solid transparent;
  margin-bottom: -2px;
  transition: all .3s;
}

.tab.active {
  color: #1890ff;
  border-bottom-color: #1890ff;
  font-weight: 600;
}

.field {
  margin-bottom: 16px;
}

.field label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 6px;
  color: #333;
}

.field input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
  transition: border-color .3s;
}

.field input:focus {
  outline: none;
  border-color: #1890ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, .2);
}

.required { color: #ff4d4f; }

.error {
  color: #ff4d4f;
  font-size: 14px;
  margin: 8px 0;
  text-align: center;
}

.btn-primary {
  width: 100%;
  padding: 12px;
  background: #1890ff;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 8px;
  transition: background .3s;
}

.btn-primary:hover:not(:disabled) {
  background: #40a9ff;
}

.btn-primary:disabled {
  opacity: .6;
  cursor: not-allowed;
}

.switch-text {
  text-align: center;
  font-size: 14px;
  color: #999;
  margin-top: 20px;
}

.switch-text a {
  color: #1890ff;
  text-decoration: none;
}

.switch-text a:hover {
  text-decoration: underline;
}
</style>
