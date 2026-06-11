<template>
  <div class="customer-manage">
    <div class="header-bar">
      <h2>顾客管理</h2>
      <button class="btn btn-primary" @click="showForm = true; form = {}; isEditing = false">+ 新增顾客</button>
    </div>
    <table class="table">
      <thead><tr><th>顾客代码</th><th>顾客名称</th><th>联系人</th><th>地址</th><th>邮编</th><th>电话</th><th>操作</th></tr></thead>
      <tbody>
        <tr v-for="c in customers" :key="c.customerId">
          <td>{{ c.customerId }}</td>
          <td>{{ c.customerName }}</td>
          <td>{{ c.contactName }}</td>
          <td>{{ c.address }}</td>
          <td>{{ c.zipCode }}</td>
          <td>{{ c.phone }}</td>
          <td>
            <button class="btn btn-sm btn-info" @click="edit(c)">编辑</button>
            <button class="btn btn-sm btn-danger" @click="handleDelete(c)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="modal" v-if="showForm">
      <div class="modal-content">
        <span class="close" @click="showForm=false">&times;</span>
        <h3>{{ form.customerId ? '编辑' : '新增' }}顾客</h3>
        <div class="form">
          <div class="form-item" v-if="isEditing"><label>顾客代码</label><input v-model="form.customerId" disabled></div>
          <div class="form-item"><label>顾客名称</label><input v-model="form.customerName"></div>
          <div class="form-item"><label>联系人</label><input v-model="form.contactName"></div>
          <div class="form-item"><label>地址</label><input v-model="form.address"></div>
          <div class="form-item"><label>邮编</label><input v-model="form.zipCode"></div>
          <div class="form-item"><label>电话</label><input v-model="form.phone"></div>
          <div class="form-actions">
            <button class="btn btn-primary" @click="handleSave">保存</button>
            <button class="btn" @click="showForm=false">取消</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api';
export default {
  name: 'CustomerManage',
  data() { return { customers: [], showForm: false, form: {}, isEditing: false }; },
  async created() {
    const res = await api.getAllCustomers();
    this.customers = res.data;
  },
  methods: {
    edit(c) { this.form = { ...c }; this.showForm = true; this.isEditing = true; },
    async handleSave() {
      if (this.isEditing) await api.updateCustomer(this.form.customerId, this.form);
      else await api.createCustomer(this.form);
      this.showForm = false;
      const res = await api.getAllCustomers();
      this.customers = res.data;
    },
    async handleDelete(c) {
      if (confirm(`确认删除顾客「${c.customerName}」？`)) {
        try {
          await api.deleteCustomer(c.customerId);
          const res = await api.getAllCustomers();
          this.customers = res.data;
        } catch (e) {
          alert('删除失败：该顾客可能有关联订单，无法删除');
        }
      }
    }
  }
};
</script>

<style scoped>
.header-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
.btn { padding: 8px 20px; border: none; border-radius: 4px; cursor: pointer; }
.btn-primary { background: #1890ff; color: #fff; }
.btn-info { background: #1890ff; color: #fff; }
.btn-danger { background: #ff4d4f; color: #fff; }
.btn-sm { padding: 4px 12px; font-size: 12px; }
.table { width: 100%; background: #fff; border-radius: 8px; overflow: hidden; }
.table th, .table td { padding: 12px 16px; text-align: left; border-bottom: 1px solid #f0f0f0; }
.table th { background: #fafafa; font-weight: 600; }
.modal { position: fixed; top:0;left:0;right:0;bottom:0; background:rgba(0,0,0,.5); display:flex; align-items:center; justify-content:center; z-index:1000; }
.modal-content { background:#fff; border-radius:8px; padding:24px; min-width:500px; position:relative; }
.close { position:absolute; top:16px; right:20px; font-size:28px; cursor:pointer; color:#999; }
.form-item { margin-bottom: 12px; }
.form-item label { display: block; margin-bottom: 4px; font-weight: 500; font-size: 14px; }
.form-item input { width: 100%; padding: 8px 12px; border: 1px solid #d9d9d9; border-radius: 4px; font-size: 14px; }
.form-actions { margin-top: 16px; display: flex; gap: 12px; }
</style>
