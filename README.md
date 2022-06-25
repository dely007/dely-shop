# dely-shop
得利商城
####git提交代码规范
----新增新功能(feature)----
feat: 新增XXX功能
----修改bug的注释----
fix: 提交XXXX

A) Service/DAO层方法命名规约
	1) 获取单个对象的方法用get做前缀。
	2) 获取多个对象的方法用list做前缀，复数形式结尾如:listObjects。 
	3) 获取统计值的方法用count做前缀。
	4) 插入的方法用save/insert做前缀。
	5) 删除的方法用remove/delete做前缀。
	6) 修改的方法用update做前缀。

B) 领域模型命名规约
  1) 数据对象:xxxDO，xxx即为数据表名。
  2) 一般数据传输对象:xxxDTO，xxx为业务领域相关的名称，项目里面也用VO。 
  3) 展示对象:xxxVO，也就是响应给前端的实体包装类。
  4) 接收前端json对象请求的命名为 XXXRequest
