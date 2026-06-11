<template>
  <div class="manufacturer-manage">
    <div class="header-bar">
      <h2>厂家管理</h2>
      <button class="btn btn-primary" @click="showForm = true; form = {}; isEditing = false">+ 新增厂家</button>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th>厂家代码</th>
          <th>厂家名称</th>
          <th>联系人</th>
          <th>联系电话</th>
          <th>地址</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="m in manufacturers" :key="m.manufacturerCode">
          <td>{{ m.manufacturerCode }}</td>
          <td>{{ m.manufacturerName }}</td>
          <td>{{ m.contactPerson }}</td>
          <td>{{ m.contactPhone }}</td>
          <td>{{ m.address }}</td>
          <td>
            <button class="btn btn-sm btn-info" @click="edit(m)">编辑</button>
            <button class="btn btn-sm btn-danger" @click="handleDelete(m)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="modal" v-if="showForm">
      <div class="modal-content">
        <span class="close" @click="showForm = false">&times;</span>
        <h3>{{ form.manufacturerCode ? '编辑' : '新增' }}厂家</h3>
        <div class="form">
          <div class="form-item" v-if="isEditing"><label>厂家代码</label><input v-model="form.manufacturerCode" disabled></div>
          <div class="form-item"><label>厂家名称</label><input v-model="form.manufacturerName"></div>
          <div class="form-item"><label>联系人</label><input v-model="form.contactPerson"></div>
          <div class="form-item"><label>联系电话</label><input v-model="form.contactPhone"></div>
          <div class="form-item"><label>地址</label><input v-model="form.address"></div>
          <div class="form-actions">
            <button class="btn btn-primary" @click="handleSave">保存</button>
            <button class="btn" @click="showForm = false">取消</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/api/index';
export default {
  name: 'ManufacturerManage',
  data() { return { manufacturers: [], showForm: false, form: {}, isEditing: false }; },
  async created() {
    const res = await api.getManufacturers();
    this.manufacturers = res.data;
  },
  methods: {
    edit(m) { this.form = { ...m }; this.showForm = true; this.isEditing = true; },
    async handleSave() {
      if (this.isEditing) await api.updateManufacturer(this.form.manufacturerCode, this.form);
      else await api.addManufacturer(this.form);
      this.showForm = false;
      const res = await api.getManufacturers();
      this.manufacturers = res.data;
    },
    async handleDelete(m) {
      if (confirm(`确认删除厂家「${m.manufacturerName}」？`)) {
        await api.deleteManufacturer(m.manufacturerCode);
        const res = await api.getManufacturers();
        this.manufacturers = res.data;
      }
    }
  }
};
</script>

<style scoped>
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.btn {
  padding: 8px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background: #1890ff;
  color: #fff;
}

.btn-info {
  background: #1890ff;
  color: #fff;
}

.btn-danger {
  background: #ff4d4f;
  color: #fff;
}

.btn-sm {
  padding: 4px 12px;
  font-size: 12px;
}

.table {
  width: 100%;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
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

.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, .5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  min-width: 500px;
  position: relative;
}

.close {
  position: absolute;
  top: 16px;
  right: 20px;
  font-size: 28px;
  cursor: pointer;
  color: #999;
}

.form-item {
  margin-bottom: 12px;
}

.form-item label {
  display: block;
  margin-bottom: 4px;
  font-weight: 500;
  font-size: 14px;
}

.form-item input {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
}

.form-actions {
  margin-top: 16px;
  display: flex;
  gap: 12px;
}
</style>
