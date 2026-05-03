--系统字典表
CREATE TABLE public.sys_dict (
     id bigserial NOT NULL, -- 主键ID
     dict_type varchar(50) NOT NULL, -- 字典类型
     dict_code varchar(50) NOT NULL, -- 字典编码
     dict_name varchar(100) NOT NULL, -- 字典名称
     sort int4 DEFAULT 0 NULL, -- 排序值
     status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
     parent_code varchar(50) DEFAULT ''::character varying NULL, -- 父级编码
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT sys_dict_pkey PRIMARY KEY (id),
     CONSTRAINT uk_dict_type_code UNIQUE (dict_type, dict_code)
);
CREATE INDEX idx_sys_dict_parent_code ON public.sys_dict USING btree (parent_code);
CREATE INDEX idx_sys_dict_type ON public.sys_dict USING btree (dict_type);
COMMENT ON TABLE public.sys_dict IS '系统字典表';

-- Column comments

COMMENT ON COLUMN public.sys_dict.id IS '主键ID';
COMMENT ON COLUMN public.sys_dict.dict_type IS '字典类型';
COMMENT ON COLUMN public.sys_dict.dict_code IS '字典编码';
COMMENT ON COLUMN public.sys_dict.dict_name IS '字典名称';
COMMENT ON COLUMN public.sys_dict.sort IS '排序值';
COMMENT ON COLUMN public.sys_dict.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.sys_dict.parent_code IS '父级编码';
COMMENT ON COLUMN public.sys_dict.create_time IS '创建时间';
COMMENT ON COLUMN public.sys_dict.update_time IS '更新时间';

CREATE TABLE public.sys_config (
   id bigserial NOT NULL,
   config_key varchar(100) NOT NULL,
   config_value varchar(500) NOT NULL,
   config_desc varchar(200) NULL,
   config_type varchar(50) NULL,
   sort int4 DEFAULT 0 NULL,
   status int2 DEFAULT 1 NULL,
   create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
   update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
   "version" int8 DEFAULT 1 NULL,
   CONSTRAINT t_sys_config_config_key_key UNIQUE (config_key),
   CONSTRAINT t_sys_config_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE public.sys_config IS '系统参数配置表';

--登录日志表
CREATE TABLE public.sys_login_log (
      id bigserial NOT NULL, -- 主键ID
      user_id int8 NULL, -- 用户ID
      login_account varchar(50) NULL, -- 登录账号
      login_ip varchar(50) NULL, -- 登录IP
      login_location varchar(100) NULL, -- 登录地点
      login_device varchar(100) NULL, -- 登录设备
      login_status int2 NULL, -- 登录状态（1-成功，0-失败）
      error_msg varchar(200) NULL, -- 错误信息（失败时）
      login_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 登录时间
      logout_time timestamptz NULL, -- 登出时间
      "version" int8 DEFAULT 1 NULL,
      CONSTRAINT sys_login_log_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_login_log_status ON public.sys_login_log USING btree (login_status);
CREATE INDEX idx_login_log_time ON public.sys_login_log USING btree (login_time);
CREATE INDEX idx_login_log_user ON public.sys_login_log USING btree (user_id);
COMMENT ON TABLE public.sys_login_log IS '登录日志表';

-- Column comments

COMMENT ON COLUMN public.sys_login_log.id IS '主键ID';
COMMENT ON COLUMN public.sys_login_log.user_id IS '用户ID';
COMMENT ON COLUMN public.sys_login_log.login_account IS '登录账号';
COMMENT ON COLUMN public.sys_login_log.login_ip IS '登录IP';
COMMENT ON COLUMN public.sys_login_log.login_location IS '登录地点';
COMMENT ON COLUMN public.sys_login_log.login_device IS '登录设备';
COMMENT ON COLUMN public.sys_login_log.login_status IS '登录状态（1-成功，0-失败）';
COMMENT ON COLUMN public.sys_login_log.error_msg IS '错误信息（失败时）';
COMMENT ON COLUMN public.sys_login_log.login_time IS '登录时间';
COMMENT ON COLUMN public.sys_login_log.logout_time IS '登出时间';

CREATE TABLE public.sys_oper_log (
     id bigserial NOT NULL,
     user_id int8 NOT NULL,
     user_account varchar(50) NOT NULL,
     oper_module varchar(50) NOT NULL,
     oper_type varchar(50) NOT NULL,
     oper_content varchar(500) NOT NULL,
     oper_ip varchar(50) NULL,
     oper_location varchar(100) NULL,
     oper_status int2 NOT NULL,
     error_msg varchar(200) NULL,
     oper_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT t_sys_oper_log_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_oper_user ON public.sys_oper_log USING btree (user_id);
COMMENT ON TABLE public.sys_oper_log IS '系统操作日志表';

--权限表
CREATE TABLE public.sys_permission (
       id bigserial NOT NULL, -- 主键ID
       perm_code varchar(100) NOT NULL, -- 权限编码（如question:add/question:delete/ai:chat）
       perm_name varchar(100) NOT NULL, -- 权限名称
       perm_type int2 NOT NULL, -- 权限类型（1-页面，2-按钮）
       parent_perm_id int8 DEFAULT 0 NULL, -- 父级权限ID
       sort int4 DEFAULT 0 NULL, -- 排序值
       status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
       create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
       update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
       "version" int8 DEFAULT 1 NULL,
       CONSTRAINT sys_permission_pkey PRIMARY KEY (id),
       CONSTRAINT uk_perm_code UNIQUE (perm_code)
);
CREATE INDEX idx_perm_parent_id ON public.sys_permission USING btree (parent_perm_id);
CREATE INDEX idx_perm_type ON public.sys_permission USING btree (perm_type);
COMMENT ON TABLE public.sys_permission IS '权限表';

-- Column comments

COMMENT ON COLUMN public.sys_permission.id IS '主键ID';
COMMENT ON COLUMN public.sys_permission.perm_code IS '权限编码（如question:add/question:delete/ai:chat）';
COMMENT ON COLUMN public.sys_permission.perm_name IS '权限名称';
COMMENT ON COLUMN public.sys_permission.perm_type IS '权限类型（1-页面，2-按钮）';
COMMENT ON COLUMN public.sys_permission.parent_perm_id IS '父级权限ID';
COMMENT ON COLUMN public.sys_permission.sort IS '排序值';
COMMENT ON COLUMN public.sys_permission.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.sys_permission.create_time IS '创建时间';
COMMENT ON COLUMN public.sys_permission.update_time IS '更新时间';

--用户表
CREATE TABLE public.sys_user (
     id bigserial NOT NULL, -- 主键ID
     user_account varchar(50) NOT NULL, -- 登录账号
     user_password varchar(100) NOT NULL, -- 密码（加密存储）
     user_name varchar(50) NULL, -- 用户姓名
     user_type varchar(50) NULL, -- 用户类型（对应字典表中字段dict_type，dict_code）
     school_id int8 NULL, -- 关联学校ID
     stage_code varchar(50) NULL, -- 学段编码（对应字典表中字段dict_type，dict_code）
     phone varchar(20) NULL, -- 联系电话
     email varchar(100) NULL, -- 邮箱
     avatar varchar(200) NULL, -- 头像地址
     status int2 DEFAULT 1 NULL, -- 状态（状态：1-启用，0-禁用）
     last_login_time timestamptz NULL, -- 最后登录时间
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     grade_id int8 NULL, -- 关联年级id
     classes_id int8 NULL, -- 关联班级id
     CONSTRAINT sys_user_pkey PRIMARY KEY (id),
     CONSTRAINT uk_user_account UNIQUE (user_account)
);
CREATE INDEX idx_user_school ON public.sys_user USING btree (school_id);
CREATE INDEX idx_user_type ON public.sys_user USING btree (user_type);
COMMENT ON TABLE public.sys_user IS '用户表';

-- Column comments

COMMENT ON COLUMN public.sys_user.id IS '主键ID';
COMMENT ON COLUMN public.sys_user.user_account IS '登录账号';
COMMENT ON COLUMN public.sys_user.user_password IS '密码（加密存储）';
COMMENT ON COLUMN public.sys_user.user_name IS '用户姓名';
COMMENT ON COLUMN public.sys_user.user_type IS '用户类型（对应字典表中字段dict_type，dict_code）';
COMMENT ON COLUMN public.sys_user.school_id IS '关联学校ID';
COMMENT ON COLUMN public.sys_user.stage_code IS '学段编码（对应字典表中字段dict_type，dict_code）';
COMMENT ON COLUMN public.sys_user.phone IS '联系电话';
COMMENT ON COLUMN public.sys_user.email IS '邮箱';
COMMENT ON COLUMN public.sys_user.avatar IS '头像地址';
COMMENT ON COLUMN public.sys_user.status IS '状态（状态：1-启用，0-禁用）';
COMMENT ON COLUMN public.sys_user.last_login_time IS '最后登录时间';
COMMENT ON COLUMN public.sys_user.create_time IS '创建时间';
COMMENT ON COLUMN public.sys_user.update_time IS '更新时间';
COMMENT ON COLUMN public.sys_user.grade_id IS '关联年级id';
COMMENT ON COLUMN public.sys_user.classes_id IS '关联班级id';

--用户权限关联表
CREATE TABLE public.sys_user_permission (
    id bigserial NOT NULL, -- 主键ID
    user_id int8 NOT NULL, -- 用户ID
    perm_id int8 NOT NULL, -- 权限ID
    create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
    update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
    "version" int8 DEFAULT 1 NULL,
    CONSTRAINT sys_user_permission_pkey PRIMARY KEY (id),
    CONSTRAINT uk_user_perm UNIQUE (user_id, perm_id)
);
CREATE INDEX idx_user_perm_perm ON public.sys_user_permission USING btree (perm_id);
CREATE INDEX idx_user_perm_user ON public.sys_user_permission USING btree (user_id);
COMMENT ON TABLE public.sys_user_permission IS '用户权限关联表';

-- Column comments

COMMENT ON COLUMN public.sys_user_permission.id IS '主键ID';
COMMENT ON COLUMN public.sys_user_permission.user_id IS '用户ID';
COMMENT ON COLUMN public.sys_user_permission.perm_id IS '权限ID';
COMMENT ON COLUMN public.sys_user_permission.create_time IS '创建时间';
COMMENT ON COLUMN public.sys_user_permission.update_time IS '更新时间';


CREATE TABLE public.t_ai_chat_conversation (
       id bigserial NOT NULL,
       conversation_id varchar(64) NOT NULL, -- 来源会话ID，追溯记忆来源
       user_id int8 NOT NULL, -- 用户id
       conversation_name varchar(100) NULL, -- 记忆类别/标识
       chat_type varchar(50) NULL,
       last_msg_time timestamptz NULL,
       status int2 DEFAULT 1 NULL,
       create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
       update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
       "version" int8 DEFAULT 1 NULL,
       conversation_value text NULL, -- 记忆内容
       importance float8 NULL, -- 重要度评分（0.0~1.0），用于记忆淘汰策略
       CONSTRAINT t_ai_chat_conversation_conversation_id_key UNIQUE (conversation_id),
       CONSTRAINT t_ai_chat_conversation_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_chat_user ON public.t_ai_chat_conversation USING btree (user_id);
COMMENT ON TABLE public.t_ai_chat_conversation IS 'AI对话会话表(长期记忆)';

-- Column comments

COMMENT ON COLUMN public.t_ai_chat_conversation.conversation_id IS '来源会话ID，追溯记忆来源';
COMMENT ON COLUMN public.t_ai_chat_conversation.user_id IS '用户id';
COMMENT ON COLUMN public.t_ai_chat_conversation.conversation_name IS '记忆类别/标识';
COMMENT ON COLUMN public.t_ai_chat_conversation.conversation_value IS '记忆内容';
COMMENT ON COLUMN public.t_ai_chat_conversation.importance IS '重要度评分（0.0~1.0），用于记忆淘汰策略';

--记录用户与ai对话信息
CREATE TABLE public.t_ai_chat_message (
      id int8 DEFAULT nextval('ai_chat_message_id_seq'::regclass) NOT NULL,
      conversation_id varchar(64) NOT NULL, -- 对话ID
      message_type varchar(32) NOT NULL, -- 消息类型：USER、ASSISTANT、SYSTEM等
      "content" text NOT NULL, -- 消息内容
      "role" varchar(32) NOT NULL, -- 角色：user、assistant、system
      create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
      "version" int8 DEFAULT 1 NULL,
      user_id int8 NULL, -- 关联用户ID
      CONSTRAINT ai_chat_message_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_ai_chat_message_conversation_id ON public.t_ai_chat_message USING btree (conversation_id);
COMMENT ON TABLE public.t_ai_chat_message IS '记录用户与ai对话信息';

-- Column comments

COMMENT ON COLUMN public.t_ai_chat_message.conversation_id IS '对话ID';
COMMENT ON COLUMN public.t_ai_chat_message.message_type IS '消息类型：USER、ASSISTANT、SYSTEM等';
COMMENT ON COLUMN public.t_ai_chat_message."content" IS '消息内容';
COMMENT ON COLUMN public.t_ai_chat_message."role" IS '角色：user、assistant、system';
COMMENT ON COLUMN public.t_ai_chat_message.create_time IS '创建时间';
COMMENT ON COLUMN public.t_ai_chat_message.user_id IS '关联用户ID';

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
        create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
        update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
        "version" int8 DEFAULT 1 NULL,
        CONSTRAINT ai_question_batch_pkey PRIMARY KEY (id),
        CONSTRAINT uk_batch_no UNIQUE (batch_no)
);
CREATE INDEX idx_batch_create_time ON public.t_ai_question_batch USING btree (create_time);
CREATE INDEX idx_batch_creator ON public.t_ai_question_batch USING btree (creator_id);
CREATE INDEX idx_batch_generate_status ON public.t_ai_question_batch USING btree (generate_status);
COMMENT ON TABLE public.t_ai_question_batch IS 'AI生成题目批次表';

-- Column comments

COMMENT ON COLUMN public.t_ai_question_batch.id IS '主键ID';
COMMENT ON COLUMN public.t_ai_question_batch.batch_no IS '批次编号';
COMMENT ON COLUMN public.t_ai_question_batch.creator_id IS '创建人ID';
COMMENT ON COLUMN public.t_ai_question_batch.subject_id IS '科目ID';
COMMENT ON COLUMN public.t_ai_question_batch.stage_code IS '学段编码';
COMMENT ON COLUMN public.t_ai_question_batch.grade_code IS '年级编码';
COMMENT ON COLUMN public.t_ai_question_batch.kp_ids IS '知识点ID列表（逗号分隔）';
COMMENT ON COLUMN public.t_ai_question_batch.question_type_code IS '题型编码';
COMMENT ON COLUMN public.t_ai_question_batch.difficulty_code IS '难度编码';
COMMENT ON COLUMN public.t_ai_question_batch.question_count IS '生成题目数量';
COMMENT ON COLUMN public.t_ai_question_batch.generate_status IS '生成状态（0-待生成，1-生成中，2-生成完成，3-生成失败）';
COMMENT ON COLUMN public.t_ai_question_batch.success_count IS '成功生成数量';
COMMENT ON COLUMN public.t_ai_question_batch.fail_count IS '失败数量';
COMMENT ON COLUMN public.t_ai_question_batch.error_msg IS '失败原因';
COMMENT ON COLUMN public.t_ai_question_batch.create_time IS '创建时间';
COMMENT ON COLUMN public.t_ai_question_batch.update_time IS '更新时间';


--AI生成题目表
CREATE TABLE public.t_ai_question_vector (
     id int8 DEFAULT nextval('ai_question_id_seq'::regclass) NOT NULL, -- 主键ID
     question_no varchar(50) NULL, -- 题目编号
     subject_id int8 NULL, -- 科目ID
     stage_code varchar(50) NULL, -- 学段编码
     grade_id int8 NULL, -- 年级id
     kp_id int8 NULL, -- 知识点ID
     question_type_code varchar(50) NULL, -- 题型编码（关联sys_dict）
     difficulty_code varchar(50) NULL, -- 难度编码（关联sys_dict）
     "content" text NULL, -- 题目内容
     question_analysis text NULL, -- 题目解析
     question_answer text NULL, -- 题目答案
     embedding public.vector NULL, -- 题目内容向量表示
     creator_id int8 NULL, -- 创建人ID（老师/校长）
     generate_batch_no varchar(50) NULL, -- 生成批次号（同一批生成的题目）
     status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     metadata json NULL, -- 元数据
     question_score int4 DEFAULT 5 NULL, -- 题目分值
     source_kb_id int8 NULL, -- 来源知识库ID
     CONSTRAINT ai_question_pkey PRIMARY KEY (id),
     CONSTRAINT uk_question_no UNIQUE (question_no)
);
CREATE INDEX idx_question_batch ON public.t_ai_question_vector USING btree (generate_batch_no);
CREATE INDEX idx_question_creator ON public.t_ai_question_vector USING btree (creator_id);
CREATE INDEX idx_question_kp ON public.t_ai_question_vector USING btree (kp_id);
CREATE INDEX idx_question_subject_stage ON public.t_ai_question_vector USING btree (subject_id, stage_code);
CREATE INDEX idx_question_type_difficulty ON public.t_ai_question_vector USING btree (question_type_code, difficulty_code);
COMMENT ON TABLE public.t_ai_question_vector IS 'AI生成题目表';

-- Column comments

COMMENT ON COLUMN public.t_ai_question_vector.id IS '主键ID';
COMMENT ON COLUMN public.t_ai_question_vector.question_no IS '题目编号';
COMMENT ON COLUMN public.t_ai_question_vector.subject_id IS '科目ID';
COMMENT ON COLUMN public.t_ai_question_vector.stage_code IS '学段编码';
COMMENT ON COLUMN public.t_ai_question_vector.grade_id IS '年级id';
COMMENT ON COLUMN public.t_ai_question_vector.kp_id IS '知识点ID';
COMMENT ON COLUMN public.t_ai_question_vector.question_type_code IS '题型编码（关联sys_dict）';
COMMENT ON COLUMN public.t_ai_question_vector.difficulty_code IS '难度编码（关联sys_dict）';
COMMENT ON COLUMN public.t_ai_question_vector."content" IS '题目内容';
COMMENT ON COLUMN public.t_ai_question_vector.question_analysis IS '题目解析';
COMMENT ON COLUMN public.t_ai_question_vector.question_answer IS '题目答案';
COMMENT ON COLUMN public.t_ai_question_vector.embedding IS '题目内容向量表示';
COMMENT ON COLUMN public.t_ai_question_vector.creator_id IS '创建人ID（老师/校长）';
COMMENT ON COLUMN public.t_ai_question_vector.generate_batch_no IS '生成批次号（同一批生成的题目）';
COMMENT ON COLUMN public.t_ai_question_vector.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_ai_question_vector.create_time IS '创建时间';
COMMENT ON COLUMN public.t_ai_question_vector.update_time IS '更新时间';
COMMENT ON COLUMN public.t_ai_question_vector.metadata IS '元数据';
COMMENT ON COLUMN public.t_ai_question_vector.question_score IS '题目分值';
COMMENT ON COLUMN public.t_ai_question_vector.source_kb_id IS '来源知识库ID';

CREATE TABLE public.t_classes_info (
       id bigserial NOT NULL, -- 主键ID
       class_code varchar(30) NULL, -- 班级编码
       class_name varchar(50) NULL, -- 班级名称（1班、实验班、凌云班等）
       grade_id int8 NULL, -- 所属年级ID
       school_id int8 NULL, -- 所属学校ID
       status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
       create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
       update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
       "version" int8 DEFAULT 1 NULL, -- 版本号
       CONSTRAINT classes_pkey PRIMARY KEY (id),
       CONSTRAINT uk_class_code UNIQUE (class_code)
);
CREATE INDEX idx_classes_grade_id ON public.t_classes_info USING btree (grade_id);
CREATE INDEX idx_classes_school_id ON public.t_classes_info USING btree (school_id);
COMMENT ON TABLE public.t_classes_info IS '班级表';

-- Column comments

COMMENT ON COLUMN public.t_classes_info.id IS '主键ID';
COMMENT ON COLUMN public.t_classes_info.class_code IS '班级编码';
COMMENT ON COLUMN public.t_classes_info.class_name IS '班级名称（1班、实验班、凌云班等）';
COMMENT ON COLUMN public.t_classes_info.grade_id IS '所属年级ID';
COMMENT ON COLUMN public.t_classes_info.school_id IS '所属学校ID';
COMMENT ON COLUMN public.t_classes_info.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_classes_info.create_time IS '创建时间';
COMMENT ON COLUMN public.t_classes_info.update_time IS '更新时间';
COMMENT ON COLUMN public.t_classes_info."version" IS '版本号';

CREATE TABLE public.t_grade_info (
     id bigserial NOT NULL, -- 主键ID
     grade_code varchar(30) NULL, -- 年级编码
     grade_name varchar(50) NULL, -- 年级名称（一年级、初一、高一等）
     school_id int8 NULL, -- 所属学校ID
     status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL, -- 版本号
     CONSTRAINT grade_pkey PRIMARY KEY (id),
     CONSTRAINT uk_grade_code UNIQUE (grade_code)
);
CREATE INDEX idx_grade_info_school_id ON public.t_grade_info USING btree (school_id);
COMMENT ON TABLE public.t_grade_info IS '年级表';

-- Column comments

COMMENT ON COLUMN public.t_grade_info.id IS '主键ID';
COMMENT ON COLUMN public.t_grade_info.grade_code IS '年级编码';
COMMENT ON COLUMN public.t_grade_info.grade_name IS '年级名称（一年级、初一、高一等）';
COMMENT ON COLUMN public.t_grade_info.school_id IS '所属学校ID';
COMMENT ON COLUMN public.t_grade_info.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_grade_info.create_time IS '创建时间';
COMMENT ON COLUMN public.t_grade_info.update_time IS '更新时间';
COMMENT ON COLUMN public.t_grade_info."version" IS '版本号';


CREATE TABLE public.t_knowledge_base (
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
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
     "version" int8 DEFAULT 1 NULL,
     metadata json NULL,
     CONSTRAINT knowledge_base_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_kb_creator ON public.t_knowledge_base USING btree (creator_id);
CREATE INDEX idx_kb_subject_stage ON public.t_knowledge_base USING btree (subject_id, stage_code);
COMMENT ON TABLE public.t_knowledge_base IS '知识库表';

-- Column comments

COMMENT ON COLUMN public.t_knowledge_base.id IS '主键ID';
COMMENT ON COLUMN public.t_knowledge_base.kb_name IS '知识库名称';
COMMENT ON COLUMN public.t_knowledge_base.kb_type IS '知识库类型（如教材/教辅/真题）';
COMMENT ON COLUMN public.t_knowledge_base.subject_id IS '关联科目ID';
COMMENT ON COLUMN public.t_knowledge_base.stage_code IS '学段编码';
COMMENT ON COLUMN public.t_knowledge_base.file_name IS '文件名称';
COMMENT ON COLUMN public.t_knowledge_base.file_path IS '文件存储路径';
COMMENT ON COLUMN public.t_knowledge_base.file_size IS '文件大小（字节）';
COMMENT ON COLUMN public.t_knowledge_base."content" IS '文档文本内容';
COMMENT ON COLUMN public.t_knowledge_base.embedding IS '文档内容向量表示';
COMMENT ON COLUMN public.t_knowledge_base.creator_id IS '创建人ID';
COMMENT ON COLUMN public.t_knowledge_base.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_knowledge_base.create_time IS '创建时间';
COMMENT ON COLUMN public.t_knowledge_base.update_time IS '更新时间';

CREATE TABLE public.t_knowledge_base_chunk_vector (
      id int8 DEFAULT nextval('t_knowledge_base_chunk_id_seq'::regclass) NOT NULL,
      kb_id int8 NOT NULL,
      chunk_no int4 NOT NULL,
      chunk_content text NOT NULL,
      embedding public.vector NULL,
      chunk_size int4 NULL,
      metadata json NULL,
      status int2 DEFAULT 1 NULL,
      create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
      update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
      "version" int8 DEFAULT 1 NULL,
      CONSTRAINT t_knowledge_base_chunk_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_chunk_kb ON public.t_knowledge_base_chunk_vector USING btree (kb_id);
COMMENT ON TABLE public.t_knowledge_base_chunk_vector IS '知识库文档分块表';

CREATE TABLE public.t_knowledge_point (
      id int8 DEFAULT nextval('knowledge_point_id_seq'::regclass) NOT NULL, -- 主键ID
      kp_code varchar(50) NOT NULL, -- 知识点编码
      kp_name varchar(100) NOT NULL, -- 知识点名称
      subject_id int8 NOT NULL, -- 关联科目ID
      parent_kp_id int8 DEFAULT 0 NULL, -- 父级知识点ID
      stage_code varchar(50) NOT NULL, -- 学段编码
      sort int4 DEFAULT 0 NULL, -- 排序值
      status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
      create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
      update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
      "version" int8 DEFAULT 1 NULL,
      CONSTRAINT knowledge_point_pkey PRIMARY KEY (id),
      CONSTRAINT uk_kp_code_subject UNIQUE (kp_code, subject_id)
);
CREATE INDEX idx_kp_parent_id ON public.t_knowledge_point USING btree (parent_kp_id);
CREATE INDEX idx_kp_subject_stage ON public.t_knowledge_point USING btree (subject_id, stage_code);
COMMENT ON TABLE public.t_knowledge_point IS '知识点表';

-- Column comments

COMMENT ON COLUMN public.t_knowledge_point.id IS '主键ID';
COMMENT ON COLUMN public.t_knowledge_point.kp_code IS '知识点编码';
COMMENT ON COLUMN public.t_knowledge_point.kp_name IS '知识点名称';
COMMENT ON COLUMN public.t_knowledge_point.subject_id IS '关联科目ID';
COMMENT ON COLUMN public.t_knowledge_point.parent_kp_id IS '父级知识点ID';
COMMENT ON COLUMN public.t_knowledge_point.stage_code IS '学段编码';
COMMENT ON COLUMN public.t_knowledge_point.sort IS '排序值';
COMMENT ON COLUMN public.t_knowledge_point.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_knowledge_point.create_time IS '创建时间';
COMMENT ON COLUMN public.t_knowledge_point.update_time IS '更新时间';


CREATE TABLE public.t_paper_info (
     id bigserial NOT NULL,
     paper_no varchar(50) NOT NULL,
     paper_name varchar(200) NOT NULL,
     user_id int8 NOT NULL,
     subject_id int8 NOT NULL,
     stage_code varchar(50) NOT NULL,
     grade_code varchar(50) NOT NULL,
     total_score int4 DEFAULT 100 NOT NULL,
     question_count int4 NOT NULL,
     paper_type varchar(50) NULL,
     difficulty_ratio varchar(100) NULL,
     status int2 DEFAULT 1 NULL,
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT t_paper_info_paper_no_key UNIQUE (paper_no),
     CONSTRAINT t_paper_info_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_paper_subject ON public.t_paper_info USING btree (subject_id);
CREATE INDEX idx_paper_user ON public.t_paper_info USING btree (user_id);
COMMENT ON TABLE public.t_paper_info IS '试卷信息表';

CREATE TABLE public.t_paper_question_relation (
      id bigserial NOT NULL,
      paper_id int8 NOT NULL,
      question_id int8 NOT NULL,
      question_score int4 NOT NULL,
      question_sort int4 NOT NULL,
      create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
      update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
      "version" int8 DEFAULT 1 NULL,
      CONSTRAINT t_paper_question_relation_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_pqr_paper ON public.t_paper_question_relation USING btree (paper_id);
COMMENT ON TABLE public.t_paper_question_relation IS '试卷题目关联表';

CREATE TABLE public.t_question_collect (
       id bigserial NOT NULL,
       user_id int8 NOT NULL,
       question_id int8 NOT NULL,
       collect_note varchar(200) NULL,
       create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
       update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
       "version" int8 DEFAULT 1 NULL,
       CONSTRAINT t_question_collect_pkey PRIMARY KEY (id)
);
COMMENT ON TABLE public.t_question_collect IS '用户题目收藏表';


CREATE TABLE public.t_question_error (
     id bigserial NOT NULL,
     user_id int8 NOT NULL,
     question_id int8 NOT NULL,
     error_reason varchar(200) NULL,
     error_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
     review_status int2 DEFAULT 0 NULL,
     create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
     update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
     "version" int8 DEFAULT 1 NULL,
     CONSTRAINT t_question_error_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_error_user ON public.t_question_error USING btree (user_id);
COMMENT ON TABLE public.t_question_error IS '用户错题表';

CREATE TABLE public.t_school_info (
      id int8 DEFAULT nextval('school_info_id_seq'::regclass) NOT NULL, -- 主键ID
      school_code varchar(30) NULL, -- 学校编码
      school_name varchar(100) NOT NULL, -- 学校名称
      school_type int2 DEFAULT 1 NULL, -- 学校类型（1-总校，2-分校）
      parent_school_id int8 DEFAULT 0 NULL, -- 父级学校ID（分校关联总校）
      province varchar(50) NULL, -- 省份
      city varchar(50) NULL, -- 城市
      address varchar(200) NULL, -- 详细地址
      contact_phone varchar(20) NULL, -- 联系电话
      status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
      create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
      update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
      "version" int8 DEFAULT 1 NULL,
      CONSTRAINT school_info_pkey PRIMARY KEY (id),
      CONSTRAINT uk_school_code UNIQUE (school_code)
);
CREATE INDEX idx_school_parent_id ON public.t_school_info USING btree (parent_school_id);
CREATE INDEX idx_school_province_city ON public.t_school_info USING btree (province, city);
COMMENT ON TABLE public.t_school_info IS '学校表';

-- Column comments

COMMENT ON COLUMN public.t_school_info.id IS '主键ID';
COMMENT ON COLUMN public.t_school_info.school_code IS '学校编码';
COMMENT ON COLUMN public.t_school_info.school_name IS '学校名称';
COMMENT ON COLUMN public.t_school_info.school_type IS '学校类型（1-总校，2-分校）';
COMMENT ON COLUMN public.t_school_info.parent_school_id IS '父级学校ID（分校关联总校）';
COMMENT ON COLUMN public.t_school_info.province IS '省份';
COMMENT ON COLUMN public.t_school_info.city IS '城市';
COMMENT ON COLUMN public.t_school_info.address IS '详细地址';
COMMENT ON COLUMN public.t_school_info.contact_phone IS '联系电话';
COMMENT ON COLUMN public.t_school_info.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_school_info.create_time IS '创建时间';
COMMENT ON COLUMN public.t_school_info.update_time IS '更新时间';

CREATE TABLE public.t_subject_info (
       id int8 DEFAULT nextval('subject_info_id_seq'::regclass) NOT NULL, -- 主键ID
       subject_code varchar(30) NULL, -- 科目编码
       subject_name varchar(50) NULL, -- 科目名称（如语文、数学、英语）
       stage_code varchar(50) NULL, -- 学段编码（关联sys_dict的stage类型）
       sort int4 DEFAULT 0 NULL, -- 排序值
       status int2 DEFAULT 1 NULL, -- 状态（1-启用，0-禁用）
       create_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 创建时间
       update_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL, -- 更新时间
       "version" int8 DEFAULT 1 NULL,
       CONSTRAINT subject_info_pkey PRIMARY KEY (id),
       CONSTRAINT uk_subject_code_stage UNIQUE (subject_code, stage_code)
);
CREATE INDEX idx_subject_stage ON public.t_subject_info USING btree (stage_code);
COMMENT ON TABLE public.t_subject_info IS '科目表';

-- Column comments

COMMENT ON COLUMN public.t_subject_info.id IS '主键ID';
COMMENT ON COLUMN public.t_subject_info.subject_code IS '科目编码';
COMMENT ON COLUMN public.t_subject_info.subject_name IS '科目名称（如语文、数学、英语）';
COMMENT ON COLUMN public.t_subject_info.stage_code IS '学段编码（关联sys_dict的stage类型）';
COMMENT ON COLUMN public.t_subject_info.sort IS '排序值';
COMMENT ON COLUMN public.t_subject_info.status IS '状态（1-启用，0-禁用）';
COMMENT ON COLUMN public.t_subject_info.create_time IS '创建时间';
COMMENT ON COLUMN public.t_subject_info.update_time IS '更新时间';