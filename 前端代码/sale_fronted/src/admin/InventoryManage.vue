<template>
  <div class="inventory">
    <div class="header-bar">
      <h2>库存管理</h2>
      <button class="btn btn-primary" @click="showForm = true; form = {}; isEditing = false">+ 新增商品</button>
    </div>

    <table class="table">
      <thead>
        <tr>
          <th>商品编号</th>
          <th>分类编码</th>
          <th>商品说明</th>
          <th>单价</th>
          <th>库存</th>
          <th>生产厂家</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in products" :key="p.productId" :class="{ 'low-stock': p.stockQuantity < 50 }">
          <td>{{ p.productId }}</td>
          <td>{{ p.categoryCode }}</td>
          <td>{{ p.description }}</td>
          <td>{{ p.unitPrice }} 元</td>
          <td><span :class="p.stockQuantity < 50 ? 'danger' : ''">{{ p.stockQuantity }}</span></td>
          <td>{{ p.manufacturerName }}</td>
          <td>
            <button class="btn btn-sm btn-info" @click="edit(p)">编辑</button>
            <button class="btn btn-sm btn-danger" @click="handleDelete(p)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 新增/编辑弹窗 -->
    <div class="modal" v-if="showForm">
      <div class="modal-content">
        <span class="close" @click="showForm = false">&times;</span>
        <h3>{{ form.productId ? '编辑' : '新增' }}商品</h3>
        <div class="form">
          <div class="form-item" v-if="isEditing"><label>商品编号</label><input v-model="form.productId" disabled></div>
          <div class="form-item"><label>分类编码</label><input v-model="form.categoryCode"></div>
          <div class="form-item"><label>商品说明</label><input v-model="form.description"></div>
          <div class="form-item"><label>单价</label><input type="number" v-model="form.unitPrice"></div>
          <div class="form-item"><label>库存数量</label><input type="number" v-model="form.stockQuantity"></div>
          <div class="form-item"><label>生产厂家</label>
            <select v-model="form.manufacturerCode">
              <option v-for="m in manufacturers" :key="m.manufacturerCode" :value="m.manufacturerCode">{{
                m.manufacturerName }}</option>
            </select>
          </div>
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
  name: 'InventoryManage',
  data() { return { products: [], manufacturers: [], showForm: false, isEditing: false, form: {} }; },
  async created() { await this.loadData(); },
  methods: {
    async loadData() {
      const [p, m] = await Promise.all([api.getProducts(), api.getManufacturers()]);
      this.products = p.data;
      this.manufacturers = m.data;
    },
    edit(p) { this.form = { ...p }; this.showForm = true; this.isEditing = true; },
    async handleSave() {
      if (this.isEditing) {
        await api.updateProduct(this.form.productId, this.form);
      } else {
        await api.addProduct(this.form);
      }
      this.showForm = false;
      this.loadData();
    },
    async handleDelete(p) {
      if (confirm(`确认删除商品「${p.description}」？`)) {
        await api.deleteProduct(p.productId);
        this.loadData();
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

.low-stock {
  background: #fff2f0;
}

.danger {
  color: #ff4d4f;
  font-weight: bold;
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
  min-width: 450px;
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

.form-item input,
.form-item select {
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
