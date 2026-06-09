-- ============================================
-- 体育用品批发销售信息系统 - 数据库初始化
-- ============================================

CREATE DATABASE IF NOT EXISTS sales_information_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sales_information_system;

-- 1. 顾客表
DROP TABLE IF EXISTS order_detail;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS manufacturer;

CREATE TABLE customer (
    customer_id   VARCHAR(20)  PRIMARY KEY COMMENT '顾客代码',
    customer_name VARCHAR(100) NOT NULL    COMMENT '顾客名称',
    contact_name  VARCHAR(50)  NOT NULL    COMMENT '联系人姓名',
    address       VARCHAR(200)             COMMENT '地址',
    zip_code      VARCHAR(10)              COMMENT '邮编',
    phone         VARCHAR(20)              COMMENT '电话'
) COMMENT '顾客信息';

-- 2. 生产厂家表
CREATE TABLE manufacturer (
    manufacturer_code VARCHAR(20)  PRIMARY KEY COMMENT '厂家代码',
    manufacturer_name VARCHAR(100) NOT NULL    COMMENT '厂家名称',
    contact_person    VARCHAR(50)              COMMENT '联系人',
    contact_phone     VARCHAR(20)              COMMENT '联系电话',
    address           VARCHAR(200)             COMMENT '地址'
) COMMENT '生产厂家信息';

-- 3. 库存商品表
CREATE TABLE product (
    product_id        VARCHAR(20)  PRIMARY KEY COMMENT '商品编号',
    category_code     VARCHAR(20)  NOT NULL    COMMENT '商品分类编码',
    manufacturer_code VARCHAR(20)  NOT NULL    COMMENT '生产厂家编码',
    description       VARCHAR(200) NOT NULL    COMMENT '商品说明',
    unit_price        DECIMAL(10,2) NOT NULL   COMMENT '单价',
    stock_quantity    INT DEFAULT 0            COMMENT '库存数量',
    FOREIGN KEY (manufacturer_code) REFERENCES manufacturer(manufacturer_code)
) COMMENT '库存商品信息';

-- 厂家名称唯一约束（用于 order_detail 快照外键）
ALTER TABLE manufacturer MODIFY manufacturer_name VARCHAR(100) NOT NULL UNIQUE;

-- 4. 订单表
CREATE TABLE orders (
    order_id             VARCHAR(20)  PRIMARY KEY COMMENT '订单号',
    order_date           DATE         NOT NULL    COMMENT '下单日期',
    customer_id          VARCHAR(20)  NOT NULL    COMMENT '顾客代码',
    shipping_requirements VARCHAR(200)            COMMENT '运输要求',
    supply_status        VARCHAR(20)  DEFAULT 'pending' COMMENT '能否供货标志(pending/available/unavailable)',
    shipping_date        DATE                     COMMENT '运输日期',
    weight               DECIMAL(10,2)            COMMENT '货物重量(kg)',
    shipping_fee         DECIMAL(10,2)            COMMENT '运费',
    payment_info         VARCHAR(200)             COMMENT '付款信息',
    payment_status       VARCHAR(20)  DEFAULT 'unpaid' COMMENT '付款状态(unpaid/paid)',
    is_cancelled         BOOLEAN      DEFAULT FALSE     COMMENT '是否已取消',
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) COMMENT '订单信息';

-- 5. 订单明细表
CREATE TABLE order_detail (
    detail_id         INT AUTO_INCREMENT PRIMARY KEY COMMENT '细则编号',
    order_id          VARCHAR(20)  NOT NULL          COMMENT '订单号',
    product_id        VARCHAR(20)  NOT NULL          COMMENT '商品编号',
    manufacturer_name VARCHAR(100) NOT NULL          COMMENT '生产厂家名称',
    quantity          INT          NOT NULL          COMMENT '数量',
    unit_price        DECIMAL(10,2) NOT NULL         COMMENT '单价',
    total_amount      DECIMAL(10,2) NOT NULL         COMMENT '总金额',
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (manufacturer_name) REFERENCES manufacturer(manufacturer_name)
) COMMENT '订单细则';

-- =====================
-- 加入数据
-- =====================

-- 生产厂家
INSERT INTO manufacturer VALUES
('MFR001', '泰山体育器材有限公司', '张经理', '0538-1234567', '山东省泰安市体育路1号'),
('MFR002', '青岛双星运动用品厂', '李厂长', '0532-7654321', '山东省青岛市工业园路5号'),
('MFR003', '广州威健体育用品有限公司', '王总', '020-88886666', '广东省广州市天河区体育路88号'),
('MFR004', '北京奥力体育器材厂', '刘厂长', '010-66667777', '北京市大兴区工业区路12号');

-- 商品
INSERT INTO product VALUES
('P001', 'SPORT-01', 'MFR001', '专业篮球 - 7号标准球', 298.00, 200),
('P002', 'SPORT-01', 'MFR001', '训练篮球 - 5号球', 158.00, 350),
('P003', 'SPORT-02', 'MFR002', '羽毛球拍 - 碳素合金', 220.00, 150),
('P004', 'SPORT-02', 'MFR002', '羽毛球 - 训练级(10只装)', 45.00, 500),
('P005', 'SPORT-03', 'MFR003', '乒乓球拍 - 专业级', 380.00, 120),
('P006', 'SPORT-03', 'MFR003', '乒乓球 - 比赛级(100只装)', 120.00, 300),
('P007', 'SPORT-04', 'MFR004', '跳绳 - 专业竞速绳', 35.00, 600),
('P008', 'SPORT-04', 'MFR004', '瑜伽垫 - 加厚防滑(10mm)', 168.00, 250),
('P009', 'SPORT-01', 'MFR003', '足球 - 5号标准球', 268.00, 180),
('P010', 'SPORT-05', 'MFR001', '泳镜 - 防雾大框', 89.00, 400),
('P011', 'SPORT-01', 'MFR001', '排球 - 5号标准球', 198.00, 120);

-- 顾客
INSERT INTO customer VALUES
('CUST001', '阳光体育运动商店', '陈老板', '上海市浦东新区张杨路500号', '200120', '021-50501234'),
('CUST002', '活力体育用品超市', '赵经理', '北京市朝阳区建国路88号', '100022', '010-85885555'),
('CUST003', '强健体育俱乐部', '周教练', '广州市天河区体育西路100号', '510620', '020-38781234'),
('CUST004', '校园体育器材中心', '吴老师', '武汉市洪山区珞喻路152号', '430079', '027-67861234'),
('CUST005', '巅峰运动旗舰店', '郑店长', '成都市锦江区红星路三段99号', '610021', '028-86701234');

-- 订单
INSERT INTO orders VALUES
('ORD202501001', '2025-01-15', 'CUST001', '标准运输，请轻拿轻放', 'available', '2025-01-18', 15.50, 30.00, '微信支付', 'paid', FALSE),
('ORD202501002', '2025-01-20', 'CUST002', '加急运输，次日达', 'available', '2025-01-21', 8.20, 50.00, '银行转账', 'paid', FALSE),
('ORD202502001', '2025-02-10', 'CUST003', '普通运输', 'pending', NULL, 12.00, 25.00, '支付宝', 'unpaid', FALSE),
('ORD202502002', '2025-02-25', 'CUST004', '周末配送', 'available', '2025-02-28', 20.00, 40.00, '银行转账', 'paid', FALSE),
('ORD202503001', '2025-03-05', 'CUST005', '含发票，专线运输', 'unavailable', NULL, 5.50, 20.00, '微信支付', 'unpaid', FALSE),
('ORD202503002', '2025-03-12', 'CUST001', '工作日配送', 'available', '2025-03-15', 30.00, 60.00, '支付宝', 'paid', FALSE);

-- 订单明细
INSERT INTO order_detail VALUES
(1, 'ORD202501001', 'P001', '泰山体育器材有限公司', 10, 298.00, 2980.00),
(2, 'ORD202501001', 'P002', '泰山体育器材有限公司', 20, 158.00, 3160.00),
(3, 'ORD202501002', 'P005', '广州威健体育用品有限公司', 5, 380.00, 1900.00),
(4, 'ORD202501002', 'P006', '广州威健体育用品有限公司', 10, 120.00, 1200.00),
(5, 'ORD202502001', 'P003', '青岛双星运动用品厂', 15, 220.00, 3300.00),
(6, 'ORD202502002', 'P007', '北京奥力体育器材厂', 100, 35.00, 3500.00),
(7, 'ORD202502002', 'P008', '北京奥力体育器材厂', 30, 168.00, 5040.00),
(8, 'ORD202503001', 'P009', '广州威健体育用品有限公司', 8, 268.00, 2144.00),
(9, 'ORD202503002', 'P010', '泰山体育器材有限公司', 50, 89.00, 4450.00),
(10, 'ORD202503002', 'P004', '青岛双星运动用品厂', 200, 45.00, 9000.00);
