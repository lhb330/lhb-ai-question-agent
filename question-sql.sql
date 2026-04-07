--系统字典表
CREATE TABLE public.sys_dict (
     id bigserial NOT NULL, -- 主键ID
     dict_type varchar(50) NOT NULL, -- 字典类型（如grade/难度/difficulty/题型/question_type）
     dict_code varchar(50) NOT NULL, -- 字典编码（如grade_1=小学一年级，difficulty_2=中等）
     dict_name varchar(100) NOT NULL, -- 字典名称
     sort int4 DEFAULT 0 NULL, -- 排序值
     status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
     parent_code varchar(50) DEFAULT ''::character varying NULL, -- 父级编码
     create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT sys_dict_pkey PRIMARY KEY (id),
     CONSTRAINT uk_dict_type_code UNIQUE (dict_type, dict_code)
);
--登录日志表
CREATE TABLE public.sys_login_log (
  id bigserial NOT NULL, -- 主键ID
  user_id int8 NOT NULL, -- 用户ID
  login_account varchar(50) NOT NULL, -- 登录账号
  login_ip varchar(50) NULL, -- 登录IP
  login_location varchar(100) NULL, -- 登录地点
  login_device varchar(100) NULL, -- 登录设备
  login_status int2 NOT NULL, -- 登录状态（1-成功，0-失败）
  error_msg varchar(200) NULL, -- 错误信息（失败时）
  login_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 登录时间
  logout_time timestamp NULL, -- 登出时间
  "version" int8 DEFAULT 1 NULL,
  CONSTRAINT sys_login_log_pkey PRIMARY KEY (id)
);
--权限表
CREATE TABLE public.sys_permission (
    id bigserial NOT NULL, -- 主键ID
   perm_code varchar(100) NOT NULL, -- 权限编码（如question:add/question:delete/ai:chat）
   perm_name varchar(100) NOT NULL, -- 权限名称
   perm_type int2 NOT NULL, -- 权限类型（1-页面，2-按钮）
   parent_perm_id int8 DEFAULT 0 NULL, -- 父级权限ID
   sort int4 DEFAULT 0 NULL, -- 排序值
   status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
   create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
   update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
   "version" int8 DEFAULT 1 NULL,
   CONSTRAINT sys_permission_pkey PRIMARY KEY (id),
   CONSTRAINT uk_perm_code UNIQUE (perm_code)
);
--用户表
CREATE TABLE public.sys_user (
     id bigserial NOT NULL, -- 主键ID
     user_account varchar(50) NOT NULL, -- 登录账号
     user_password varchar(100) NOT NULL, -- 密码（加密存储）
     user_name varchar(50) NOT NULL, -- 用户姓名
     user_type int2 NOT NULL, -- 用户类型（1-校长，2-老师，3-学生）
     school_id int8 NOT NULL, -- 关联学校ID
     stage_code varchar(50) NULL, -- 学段编码（学生/老师关联）
     class_name varchar(50) NULL, -- 班级名称（学生/老师关联）
     phone varchar(20) NULL, -- 联系电话
     email varchar(100) NULL, -- 邮箱
     avatar varchar(200) NULL, -- 头像地址
     status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
     last_login_time timestamp NULL, -- 最后登录时间
     create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT sys_user_pkey PRIMARY KEY (id),
     CONSTRAINT uk_user_account UNIQUE (user_account)
);
--用户权限关联表
CREATE TABLE public.sys_user_permission (
    id bigserial NOT NULL, -- 主键ID
    user_id int8 NOT NULL, -- 用户ID
    perm_id int8 NOT NULL, -- 权限ID
    create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
    update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
    "version" int8 DEFAULT 1 NULL,
    CONSTRAINT sys_user_permission_pkey PRIMARY KEY (id),
    CONSTRAINT uk_user_perm UNIQUE (user_id, perm_id)
);
--记录用户与ai对话信息
CREATE TABLE public.t_ai_chat_message (
  id int8 DEFAULT nextval('ai_chat_message_id_seq'::regclass) NOT NULL,
  conversation_id varchar(64) NOT NULL, -- 对话ID
  message_type varchar(32) NOT NULL, -- 消息类型：USER、ASSISTANT、SYSTEM等
  "content" text NOT NULL, -- 消息内容
  "role" varchar(32) NOT NULL, -- 角色：user、assistant、system
  create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
  "version" int8 DEFAULT 1 NULL,
  CONSTRAINT ai_chat_message_pkey PRIMARY KEY (id)
);
--AI生成题目批次表
CREATE TABLE public.t_ai_question_batch (
    id int8 DEFAULT nextval('ai_question_batch_id_seq'::regclass) NOT NULL, -- 主键ID
    batch_no varchar(50) NOT NULL, -- 批次编号
    creator_id int8 NOT NULL, -- 创建人ID
    subject_id int8 NOT NULL, -- 科目ID
    stage_code varchar(50) NOT NULL, -- 学段编码
    grade_code varchar(50) NOT NULL, -- 年级编码
    kp_ids varchar(500) NOT NULL, -- 知识点ID列表（逗号分隔）
    question_type_code varchar(50) NOT NULL, -- 题型编码
    difficulty_code varchar(50) NOT NULL, -- 难度编码
    question_count int4 NOT NULL, -- 生成题目数量
    generate_status int2 DEFAULT 0 NULL, -- 生成状态（0-待生成，1-生成中，2-生成完成，3-生成失败）
    success_count int4 DEFAULT 0 NULL, -- 成功生成数量
    fail_count int4 DEFAULT 0 NULL, -- 失败数量
    error_msg varchar(500) NULL, -- 失败原因
    create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
    update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
    "version" int8 DEFAULT 1 NULL,
    CONSTRAINT ai_question_batch_pkey PRIMARY KEY (id),
    CONSTRAINT uk_batch_no UNIQUE (batch_no)
);
--AI生成题目表
CREATE TABLE public.t_ai_question_vector (
     id int8 DEFAULT nextval('ai_question_id_seq'::regclass) NOT NULL, -- 主键ID
     question_no varchar(50) NOT NULL, -- 题目编号
     subject_id int8 NOT NULL, -- 科目ID
     stage_code varchar(50) NOT NULL, -- 学段编码
     grade_code varchar(50) NOT NULL, -- 年级编码（关联sys_dict）
     kp_id int8 NOT NULL, -- 知识点ID
     question_type_code varchar(50) NOT NULL, -- 题型编码（关联sys_dict）
     difficulty_code varchar(50) NOT NULL, -- 难度编码（关联sys_dict）
     question_content text NOT NULL, -- 题目内容
     question_analysis text NULL, -- 题目解析
     question_answer text NULL, -- 题目答案
     question_embedding public.vector NULL, -- 题目内容向量表示
     creator_id int8 NOT NULL, -- 创建人ID（老师/校长）
     generate_batch_no varchar(50) NULL, -- 生成批次号（同一批生成的题目）
     status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
     create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT ai_question_pkey PRIMARY KEY (id),
     CONSTRAINT uk_question_no UNIQUE (question_no)
);
--知识库表
CREATE TABLE public.t_knowledge_base_vector (
    id int8 DEFAULT nextval('knowledge_base_id_seq'::regclass) NOT NULL, -- 主键ID
    kb_name varchar(200) NOT NULL, -- 知识库名称
    kb_type varchar(50) NULL, -- 知识库类型（如教材/教辅/真题）
    subject_id int8 NOT NULL, -- 关联科目ID
    stage_code varchar(50) NOT NULL, -- 学段编码
    file_name varchar(200) NULL, -- 文件名称
    file_path varchar(500) NULL, -- 文件存储路径
    file_size int8 NULL, -- 文件大小（字节）
    "content" text NULL, -- 文档文本内容
    embedding public.vector NULL, -- 文档内容向量表示
    creator_id int8 NOT NULL, -- 创建人ID
    status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
    create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
    update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
    "version" int8 DEFAULT 1 NULL,
    CONSTRAINT knowledge_base_pkey PRIMARY KEY (id)
);
--知识点表
CREATE TABLE public.t_knowledge_point (
  id int8 DEFAULT nextval('knowledge_point_id_seq'::regclass) NOT NULL, -- 主键ID
  kp_code varchar(50) NOT NULL, -- 知识点编码
  kp_name varchar(100) NOT NULL, -- 知识点名称
  subject_id int8 NOT NULL, -- 关联科目ID
  parent_kp_id int8 DEFAULT 0 NULL, -- 父级知识点ID
  stage_code varchar(50) NOT NULL, -- 学段编码
  sort int4 DEFAULT 0 NULL, -- 排序值
  status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
  create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
  update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
  "version" int8 DEFAULT 1 NULL,
  CONSTRAINT knowledge_point_pkey PRIMARY KEY (id),
  CONSTRAINT uk_kp_code_subject UNIQUE (kp_code, subject_id)
);
--学校表
CREATE TABLE public.t_school_info (
  id int8 DEFAULT nextval('school_info_id_seq'::regclass) NOT NULL, -- 主键ID
  school_code varchar(30) NOT NULL, -- 学校编码
  school_name varchar(100) NOT NULL, -- 学校名称
  school_type int2 DEFAULT 1 NULL, -- 学校类型（1-总校，2-分校）
  parent_school_id int8 DEFAULT 0 NULL, -- 父级学校ID（分校关联总校）
  province varchar(50) NULL, -- 省份
  city varchar(50) NULL, -- 城市
  address varchar(200) NULL, -- 详细地址
  contact_phone varchar(20) NULL, -- 联系电话
  status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
  create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
  update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
  "version" int8 DEFAULT 1 NULL,
  CONSTRAINT school_info_pkey PRIMARY KEY (id),
  CONSTRAINT uk_school_code UNIQUE (school_code)
);
--科目表
CREATE TABLE public.t_subject_info (
   id int8 DEFAULT nextval('subject_info_id_seq'::regclass) NOT NULL, -- 主键ID
   subject_code varchar(30) NOT NULL, -- 科目编码
   subject_name varchar(50) NOT NULL, -- 科目名称（如语文、数学、英语）
   stage_code varchar(50) NOT NULL, -- 学段编码（关联sys_dict的stage类型）
   sort int4 DEFAULT 0 NULL, -- 排序值
   status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
   create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
   update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
   "version" int8 DEFAULT 1 NULL,
   CONSTRAINT subject_info_pkey PRIMARY KEY (id),
   CONSTRAINT uk_subject_code_stage UNIQUE (subject_code, stage_code)
);