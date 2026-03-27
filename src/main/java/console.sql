create table employee(
   id int primary key auto_increment comment '员工ID',
   head_img varchar(20) comment '员工头像',
   name varchar(20) not null comment '员工姓名',
   gender varchar(1) default '男' comment '员工性别',
   phone varchar(11) not null comment '员工手机号',
   dept_id int default 0 comment '员工所属部门',
   position varchar(11) default '无' comment '员工职位',
   create_time varchar(20) not null comment '入职时间',
   update_time varchar(20) not null comment '最新更改时间'
)comment '员工表';

show create table dept;

show create table employee;

insert into employee
    (head_img,name,phone,create_time,update_time)
    values
           ('001.jpg','测试用户1','12345678901',now(),now()),
           ('001.jpg','测试用户2','12345678902',now(),now()),
           ('001.jpg','测试用户3','12345678903',now(),now()),
           ('001.jpg','测试用户4','12345678904',now(),now()),
           ('001.jpg','测试用户5','12345678905',now(),now()),
           ('001.jpg','测试用户6','12345678906',now(),now()),
           ('001.jpg','测试用户7','12345678907',now(),now()),
           ('001.jpg','测试用户8','12345678908',now(),now()),
           ('001.jpg','测试用户9','12345678909',now(),now()),
           ('001.jpg','测试用户10','12345678910',now(),now());

select count(*) from employee;

select * from dept;

select * from employee order by create_time desc;

select em.id as id,em.name as name,if(em.dept_id in (select de.id from dept),de.name,'无'),head_img as headImg, gender,phone,dept_id as deptId, position, em.create_time as createTime, em.update_time as updateTime
from employee as em,dept as de;

create index dept_id on employee(dept_id);

create index emp_name on employee(name);

create index emp_phone on employee(phone);

drop index emp_phone on employee;

create index emp_phone on employee(phone);

delete from employee where phone=12345678902 and dept_id=0;

select * from employee;

alter table employee change phone phone varchar(11) not null unique comment '员工手机号';

show create table employee;

select emp.id as id,emp.name as name,ifnull(de.name,'无') as deptName,head_img as headImg, gender,phone,dept_id as deptId, position, emp.create_time as createTime, emp.update_time as updateTime
    from employee as emp left join dept as de on dept_id=de.id where emp.id=1 or emp.name='' or emp.phone='' or emp.dept_id='' or emp.position='';

create table account(
    account_flag varchar(20) unique not null comment'员工账号',
    password varchar(20) not null comment '员工密码'
)comment '账号表' engine=innodb;

show create table account;

show create table employee;

show create table account;

create table class(
    class_id int primary key auto_increment comment '班级ID',
    class_name varchar(10) not null comment '班级名称',
    class_manager_teacher int default 0 comment '班主任',
    class_student_count int default 0 comment '班级人数',
    create_time varchar(20) default (date_format(now(),'%Y-%m-%d %H:%i:%s')) comment '创建时间',
    update_time varchar(20) default (date_format(now(),'%Y-%m-%d %H:%i:%s')) comment '更新时间'
)engine = innodb comment '班级表';

select version();

insert into class (class_name) values('101班'),
                                     ('102班');

create index class_name on class(class_name);
create index class_manager_teacher on class(class_manager_teacher);

alter table class change class_student_count count int default 0 comment '班级人数';

alter table class change class_manager_teacher manager int default 0 comment '班主任';

select * from class;

create index class_id_name_manager on nclass(class_id,class_name,manager);

show index from nclass;

drop index class_id_name_manager on nclass;

show index from nclass;

create index class_id_name_manager on nclass(class_id,class_name,manager);

create table student(
    id int primary key auto_increment comment '学号',
    head_image varchar(60) comment '头像',
    name varchar(20) not null comment '姓名',
    gender varchar(1) not null default '男' comment '性别',
    age int not null comment '年龄',
    from_class int not null comment '所属班级',
    manager varchar(10) not null default '无' comment '班主任',
    manager_id int not null default 0 comment '班主任Id',
    create_time varchar(20) not null default (date_format(now(),'%Y-%m-%d')) comment '入学时间',
    graduation_time varchar(20) not null default (date_format(now(),'%Y-%m-%d')) comment '毕业时间',
    update_time varchar(20) not null default (date_format(now(),'%Y-%m-%d')) comment '修改时间'
)engine=Innodb;

alter table student add column class_id int not null default 0 comment '所属班级ID';

alter table student modify from_class varchar(10) not null default '无' comment '所属班级';

create index student_id_name_clas_manager on student(id,name,from_class,manager);

show index from student;

show create table student;

alter table student modify head_image varchar(60) not null default '' comment '头像';

insert into student (name,age) values
                   ('张三',18),
                   ('李四',18),
                   ('王五',18),
                   ('赵六',18);

select * from student;

show index from student;

show create table student;

select gender,count(*) from employee group by gender;








