-- create sequence if not exists seq_aoplog_methodtaracelog_idx start with 1 increment by 1;
-- create sequence if not exists seq_aoplog_userloginlog_idx start with 1 increment by 1;
-- create sequence if not exists seq_clientsystem_idx start with 1 increment by 1;

DROP TABLE IF EXISTS CHMM_SERVICE_CONTROL_INFO;
CREATE TABLE IF NOT EXISTS CHMM_SERVICE_CONTROL_INFO
(
    SERVICE_ID        VARCHAR(40)  NOT NULL,
    RM_PATHS          VARCHAR(650) NOT NULL,
    RM_METHOD         VARCHAR(100) NOT NULL,
    APPROVAL_REQUIRED CHAR(5)      DEFAULT NULL,
    DESCRIPTION       VARCHAR(650) DEFAULT NULL,
    ERR_MESSAGE       VARCHAR(100) DEFAULT NULL,
    START_DATE        DATE         DEFAULT NULL,
    END_DATE          DATE         DEFAULT NULL,
    APPROVAL_YN       CHAR(5)      DEFAULT NULL,
    ACTIVATION        CHAR(5)      DEFAULT NULL,
    PRIMARY KEY (SERVICE_ID, RM_PATHS, RM_METHOD)
);

DROP TABLE IF EXISTS CHMM_CATEGORY_INFO;
create table CHMM_CATEGORY_INFO
(
    category_id        varchar(255)        not null,
    category_desc      varchar(1000),
    order_num          integer,
    real_value         varchar(100),
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    use_yn             CHAR(1) DEFAULT '1' not null,
    primary key (category_id)
);

DROP TABLE IF EXISTS chmm_code_info;
create table chmm_code_info
(
    category_id        varchar(255)        not null,
    code_id            varchar(255)        not null,
    code_desc          varchar(1000),
    order_num          integer,
    real_value         varchar(100),
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    use_yn             CHAR(1) DEFAULT '1' not null,
    primary key (category_id, code_id)
);

DROP TABLE IF EXISTS chmm_code_item_info;
create table chmm_code_item_info
(
    category_id        varchar(255)        not null,
    code_id            varchar(255)        not null,
    code_item_id       varchar(255)        not null,
    code_item_desc     varchar(1000),
    order_num          integer,
    real_value         varchar(100),
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    use_yn             CHAR(1) DEFAULT '1' not null,
    primary key (category_id, code_id, code_item_id)
);


DROP TABLE IF EXISTS chmm_file_metadata_info;
create table chmm_file_metadata_info
(
    file_metadata_code varchar(50) not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    file_size          varchar(10),
    file_upload_path   varchar(250),
    original_file_name varchar(100),
    upload_file_name   varchar(200),
    uri                varchar(250),
	use_yn				char(1),
    primary key (file_metadata_code)
);

DROP TABLE IF EXISTS chmm_group_info;
create table chmm_group_info
(
    group_id           varchar(255)        not null,
    group_name         varchar(255)        not null,
    group_desc         varchar(1000),
    use_yn             CHAR(1) DEFAULT '0' not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    primary key (group_id)
);

DROP TABLE IF EXISTS chmm_group_role_map;
create table chmm_group_role_map
(
    group_id       		varchar(255) not null,
    role_id        		varchar(255) not null,
    sys_insert_dtm 		timestamp,
    sys_insert_user_id 	varchar(255),
    use_yn         		CHAR(1),
    primary key (group_id, role_id)
);

DROP TABLE IF EXISTS chmm_language_info;
create table chmm_language_info
(
    country_code       varchar(5) not null,
    language_code      varchar(5) not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    description        varchar(1000),
    primary key (country_code, language_code)
);

DROP TABLE IF EXISTS chmm_mask_pattern_info;
create table chmm_mask_pattern_info
(
    pattern_id   varchar(40) not null,
    class        varchar(500),
    enabled      varchar(10),
    pattern      varchar(2000),
    pattern_name varchar(100),
    regdate      timestamp,
    regex_yn     varchar(1),
    replaces     varchar(2000),
    primary key (pattern_id)
);

DROP TABLE IF EXISTS chmm_menu_info;
create table chmm_menu_info
(
    menu_id				varchar(255) not null,
    sys_insert_dtm		timestamp,
    sys_insert_user_id	varchar(255),
    sys_update_dtm		timestamp,
    sys_update_user_id	varchar(255),
    admin_menu_yn		CHAR(1)      not null,
    left_menu_yn		CHAR(1)      not null,
    menu_desc			varchar(1000),
    menu_help_uri		varchar(255),
    menu_lvl			integer,
    menu_name			varchar(255) not null,
    menu_script			varchar(255),
    menu_seq			integer      not null,
    menu_uri			varchar(255),
    upper_menu_id		varchar(255),
    use_yn				CHAR(1)     not null,
	personal_data_yn	CHAR(1) 	DEFAULT '0',
    primary key (menu_id)
);

DROP TABLE IF EXISTS chmm_message_source_info;
create table chmm_message_source_info
(
    code               varchar(255) not null,
    country_code       varchar(5)   not null,
    language_code      varchar(5)   not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    message            varchar(1000),
    use_yn             CHAR(1) DEFAULT '0',
    primary key (code, country_code, language_code)
);

DROP TABLE IF EXISTS chmm_method_trace_log;
create table chmm_method_trace_log
(
    method_log_id           bigint    not null,
    client_ip               varchar(150),
    method_log_class_name   varchar(255),
    method_log_date         timestamp not null,
    method_log_ex_time      integer,
    method_log_hierarchy    varchar(500),
    method_log_level        varchar(50),
    method_log_method_name  varchar(255),
    method_log_msg          varchar(255),
    method_log_params       varchar(255),
    method_log_request_id   varchar(150),
    method_log_request_path varchar(500),
    method_log_request_user varchar(100),
    method_log_session_id   varchar(100),
    trans_start_date        varchar(30),
    primary key (method_log_id)
);

DROP TABLE IF EXISTS chmm_multi_role_group_info;
create table chmm_multi_role_group_info
(
    child_role_id  varchar(255) not null,
    parent_role_id varchar(255) not null,
    primary key (child_role_id, parent_role_id)
);

DROP TABLE IF EXISTS chmm_multi_user_group_info;
create table chmm_multi_user_group_info
(
    child_group_id  varchar(255) not null,
    parent_group_id varchar(255) not null,
    primary key (child_group_id, parent_group_id)
);

DROP TABLE IF EXISTS chmm_perform_his;
create table chmm_perform_his
(
    chmm_perform_his_id varchar(40)  not null,
    end_dtm             timestamp    not null,
    output_path         varchar(255) not null,
    perform_type        varchar(40)  not null,
    repeat_type         varchar(40)  not null,
    repeat_value        integer      not null,
    service_id          varchar(40)  not null,
    service_name        varchar(255) not null,
    start_dtm           timestamp    not null,
    thread_count        integer      not null,
    primary key (chmm_perform_his_id)
);

DROP TABLE IF EXISTS chmm_perform_info;
create table chmm_perform_info
(
    service_id   varchar(40) not null,
    perform_type varchar(40) not null,
    repeat_type  varchar(40) not null,
    repeat_value integer     not null,
    thread_count integer     not null,
    tps          integer     not null,
    primary key (service_id)
);

DROP TABLE IF EXISTS chmm_perform_test_case_his;
create table chmm_perform_test_case_his
(
    label               varchar(40) not null,
    chmm_perform_his_id varchar(40) not null,
    average             integer     not null,
    avgbytes            integer     not null,
    error               integer     not null,
    max                 integer     not null,
    min                 integer     not null,
    order_number        integer     not null,
    recvkb              integer     not null,
    samples             integer     not null,
    sentkb              integer     not null,
    stddev              integer     not null,
    throughput          integer     not null,
    primary key (label, chmm_perform_his_id)
);

DROP TABLE IF EXISTS chmm_perform_tree_info;
create table chmm_perform_tree_info
(
    service_id         varchar(40)         not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    service_icon       varchar(20)         not null,
    service_name       varchar(255)        not null,
    upper_folder_id    varchar(40)         not null,
    upper_folder_ids   varchar(120)        not null,
    use_yn             CHAR(1) DEFAULT '0' not null,
    primary key (service_id)
);

DROP TABLE IF EXISTS chmm_property_info;
create table chmm_property_info
(
    property_key       varchar(300) not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    property_desc      varchar(1000),
    property_value     varchar(300) not null,
    use_yn             CHAR(1),
    primary key (property_key)
);

DROP TABLE IF EXISTS chmm_resource_button_map;
create table chmm_resource_button_map
(
    button_id      varchar(255) not null,
    resource_id    varchar(255) not null,
    sys_insert_dtm timestamp,
    use_yn         CHAR(1),
    primary key (button_id, resource_id)
);

DROP TABLE IF EXISTS chmm_resource_info;
create table chmm_resource_info
(
    resource_id         	varchar(255) not null,
    sys_insert_dtm      	timestamp,
    sys_insert_user_id  	varchar(255),
    sys_update_dtm      	timestamp,
    sys_update_user_id  	varchar(255),
    resource_desc       	varchar(1000),
    resource_httpmethod 	varchar(100),
    resource_name       	varchar(255),
    resource_uri        	varchar(255) not null,
    security_order      	integer      not null,
    use_yn              	CHAR(1)      not null,
    primary key (resource_id)
);

DROP TABLE IF EXISTS chmm_resource_role_button_map;
create table chmm_resource_role_button_map
(
    button_id      varchar(255) not null,
    resource_id    varchar(255) not null,
    role_id        varchar(255) not null,
    sys_insert_dtm timestamp,
    use_yn         CHAR(1),
    primary key (button_id, resource_id, role_id)
);

DROP TABLE IF EXISTS chmm_resource_role_map;
create table chmm_resource_role_map
(
    resource_id    		varchar(255) not null,
    role_id        		varchar(255) not null,
    sys_insert_dtm 		timestamp,
	sys_insert_user_id 	varchar(255),
    use_yn         		CHAR(1),
    primary key (resource_id, role_id)
);

DROP TABLE IF EXISTS chmm_rest_assertion_his;
create table chmm_rest_assertion_his
(
    scenario_group_id   varchar(40)  not null,
    scenario_id         varchar(40)  not null,
    service_id          varchar(40)  not null,
    test_assertion_code varchar(40)  not null,
    test_comparison     varchar(40)  not null,
    test_key            varchar(200) not null,
    test_exception      varchar(200),
    test_result_detail  varchar(1000),
    test_value          varchar(200),
    primary key (scenario_group_id, scenario_id, service_id, test_assertion_code, test_comparison, test_key)
);

DROP TABLE IF EXISTS chmm_rest_assertion_info;
create table chmm_rest_assertion_info
(
    service_id          varchar(40)  not null,
    test_assertion_code varchar(40)  not null,
    test_comparison     varchar(40)  not null,
    test_key            varchar(200) not null,
    test_value          varchar(200),
    primary key (service_id, test_assertion_code, test_comparison, test_key)
);

DROP TABLE IF EXISTS chmm_rest_body_his;
create table chmm_rest_body_his
(
    scenario_group_id varchar(40) not null,
    scenario_id       varchar(40) not null,
    service_id        varchar(40) not null,
    test_body_type    varchar(200),
    test_value        bytea,
    primary key (scenario_group_id, scenario_id, service_id)
);

DROP TABLE IF EXISTS chmm_rest_body_info;
create table chmm_rest_body_info
(
    service_id     varchar(40)  not null,
    test_body_type varchar(200) not null,
    test_value     bytea,
    primary key (service_id, test_body_type)
);

DROP TABLE IF EXISTS chmm_rest_header_his;
create table chmm_rest_header_his
(
    scenario_group_id varchar(40)  not null,
    scenario_id       varchar(40)  not null,
    service_id        varchar(40)  not null,
    test_key          varchar(200) not null,
    test_value        varchar(200),
    primary key (scenario_group_id, scenario_id, service_id, test_key)
);

DROP TABLE IF EXISTS chmm_rest_header_info;
create table chmm_rest_header_info
(
    service_id varchar(40)  not null,
    test_key   varchar(200) not null,
    test_value varchar(200),
    primary key (service_id, test_key)
);

DROP TABLE IF EXISTS chmm_rest_his;
create table chmm_rest_his
(
    scenario_group_id     varchar(40) not null,
    scenario_id           varchar(40) not null,
    service_id            varchar(40) not null,
    error_message         varchar(1000),
    http_response_body    bytea,
    http_response_header  varchar(1000),
    http_response_statuts varchar(10),
    service_baseurl       varchar(1024),
    service_method        varchar(10),
    service_name          varchar(255),
    service_uri           varchar(255),
    primary key (scenario_group_id, scenario_id, service_id)
);

DROP TABLE IF EXISTS chmm_rest_info;
create table chmm_rest_info
(
    service_id      varchar(40)  not null,
    service_baseurl varchar(1024),
    service_method  varchar(10),
    service_uri     varchar(255) not null,
    primary key (service_id)
);

DROP TABLE IF EXISTS chmm_rest_scenario_group_his;
create table chmm_rest_scenario_group_his
(
    scenario_group_id varchar(40) not null,
    end_dtm           timestamp,
    start_dtm         timestamp,
    primary key (scenario_group_id)
);

DROP TABLE IF EXISTS chmm_rest_scenario_his;
create table chmm_rest_scenario_his
(
    scenario_group_id      varchar(40) not null,
    scenario_id            varchar(40) not null,
    scenario_error_message varchar(1000),
    scenario_name          varchar(255),
    primary key (scenario_group_id, scenario_id)
);

DROP TABLE IF EXISTS chmm_rest_schedule_info;
create table chmm_rest_schedule_info
(
    schedule_id        varchar(40)         not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    cron_expr          varchar(100)        not null,
    use_yn             CHAR(1) DEFAULT '0' not null,
    primary key (schedule_id)
);

DROP TABLE IF EXISTS chmm_rest_tree_info;
create table chmm_rest_tree_info
(
    service_id         varchar(40)         not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    service_icon       varchar(20)         not null,
    service_name       varchar(255)        not null,
    upper_folder_id    varchar(40)         not null,
    upper_folder_ids   varchar(120)        not null,
    use_yn             CHAR(1) DEFAULT '0' not null,
    primary key (service_id)
);

DROP TABLE IF EXISTS chmm_role_info;
create table chmm_role_info
(
    role_id            varchar(255) not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    use_yn             CHAR(1)      not null,
    role_desc          varchar(1000),
    role_end_dt        varchar(8)   not null,
    role_name          varchar(255) not null,
    role_start_dt      varchar(8)   not null,
    primary key (role_id)
);

DROP TABLE IF EXISTS chmm_role_menu_map;
create table chmm_role_menu_map
(
    menu_id        varchar(255) not null,
    role_id        varchar(255) not null,
    sys_insert_dtm timestamp,
    use_yn         CHAR(1),
    primary key (menu_id, role_id)
);


DROP TABLE IF EXISTS chmm_system_default_info;
create table chmm_system_default_info
(
    env_id             varchar(200) not null,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    env_value          varchar(2000),
    primary key (env_id)
);

DROP TABLE IF EXISTS chmm_user_group_map;
create table chmm_user_group_map
(
    group_id       		varchar(255) not null,
    user_id        		varchar(255) not null,
    sys_insert_dtm 		timestamp,
    sys_insert_user_id 	varchar(255),
    use_yn         		CHAR(1),
    primary key (group_id, user_id)
);

DROP TABLE IF EXISTS chmm_user_info;
create table chmm_user_info
(
    user_id            varchar(255)                  ,
    sys_insert_dtm     timestamp,
    sys_insert_user_id varchar(255),
    sys_update_dtm     timestamp,
    sys_update_user_id varchar(255),
    account_end_dt     VARCHAR(8) DEFAULT '99991231' ,
    password_lock_cnt  INT        DEFAULT 0          ,
    account_non_lock   CHAR(1)    DEFAULT '1'        ,
    account_start_dt   VARCHAR(8) DEFAULT '00010101' ,
    password_expire_dt VARCHAR(8) DEFAULT '99991231' ,
    user_desc          varchar(1000),
    user_email         varchar(255)                  ,
    use_yn             CHAR(1)    DEFAULT '0'        ,
    exception_send_yn  CHAR(1),
    log_send_yn        CHAR(1),
    user_img           varchar(4000),
    user_msg           varchar(4000),
    user_name          varchar(255)                  ,
    user_nick          varchar(255),
    user_pwd           varchar(255)                  ,
    user_mobile        varchar(14),
    user_snsid         varchar(255),
    user_stat_cd       varchar(16),
    primary key (user_id)
);

DROP TABLE IF EXISTS chmm_user_previous_passwords;
CREATE TABLE chmm_user_previous_passwords
(
	id            SERIAL NOT NULL PRIMARY KEY,
	user_id       VARCHAR(50)  NOT NULL,
	password_hash VARCHAR(200) NOT NULL,
	changed_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT unique_user_password UNIQUE (user_id, password_hash)
);

DROP TABLE IF EXISTS chmm_user_login_log;
create table chmm_user_login_log
(
    login_log_id     bigint    not null,
    action           varchar(30),
    action_date      timestamp not null,
    login_session_id varchar(100),
    server_ip        varchar(150),
    user_name        varchar(255),
    user_ip          varchar(150),
    primary key (login_log_id)
);

DROP TABLE IF EXISTS chmm_user_role_map;
create table chmm_user_role_map
(
    role_id        varchar(255) not null,
    user_id        varchar(255) not null,
    sys_insert_dtm timestamp,
    use_yn         CHAR(1),
    primary key (role_id, user_id)
);

DROP TABLE IF EXISTS chmm_user_token;
CREATE TABLE chmm_user_token
(
	SEQ                BIGSERIAL			 NOT NULL,
	USER_ID            varchar(255)	 NOT NULL,
	ACCESS_TOKEN       text        	 NOT NULL,
	REFRESH_TOKEN      text        	 NOT NULL,
	EXPIRY_DATE        timestamp   	 NOT NULL,
	SYS_INSERT_DTM     timestamp   	 DEFAULT NULL,
	SYS_INSERT_USER_ID varchar(255)	 DEFAULT NULL,
	PRIMARY KEY (SEQ)
);

DROP TABLE IF EXISTS chmm_board_info;
CREATE TABLE chmm_board_info
(
    board_id bigint generated by default as identity,
    board_type char(1) NOT NULL,
    title varchar(50) NOT NULL,
    mail_yn char(1) DEFAULT NULL,
    contents varchar(200) DEFAULT NULL,
    notice_yn char(1) DEFAULT NULL,
    public_yn char(1) DEFAULT NULL,
    apply_start_dtm varchar(8) DEFAULT NULL,
    apply_end_dtm varchar(8) DEFAULT NULL,
    view_cnt bigint DEFAULT 0,
    sys_insert_dtm timestamp NOT NULL DEFAULT '1970-01-01 00:00:00',
    sys_insert_user_id varchar(255) DEFAULT NULL,
    sys_update_user_id varchar(255) DEFAULT NULL,
    sys_update_dtm timestamp NOT NULL DEFAULT '1970-01-01 00:00:00',
    PRIMARY KEY (board_id)
);

DROP TABLE IF EXISTS chmm_board_user_map;
CREATE TABLE chmm_board_user_map
(
    board_id bigint NOT NULL,
    user_id varchar(255) NOT NULL,
    PRIMARY KEY (board_id, user_id)
);

DROP TABLE IF EXISTS chmm_menu_bookmark_info;
CREATE TABLE chmm_menu_bookmark_info
(
    user_id varchar(255) NOT NULL,
    menu_id varchar(255) NOT NULL,
    sys_insert_dtm timestamp NOT NULL DEFAULT '1970-01-01 00:00:00',
    sys_insert_user_id varchar(255) DEFAULT NULL,
    PRIMARY KEY (user_id, menu_id)
);

DROP TABLE IF EXISTS CHMM_ERROR_LOGGING;
CREATE TABLE CHMM_ERROR_LOGGING  (
	ERROR_LOGGING_ID			BIGSERIAL NOT NULL
	,CLIENT_ID                  VARCHAR(30)
	,CLIENT_IP                  VARCHAR(50)
	,REQUEST_URL                VARCHAR(255)
	,REQUEST_ID                 VARCHAR(20)
	,SESSION_ID                 VARCHAR(20)
	,EXCEPTION_OCCUR_LINE       VARCHAR(255)
	,EXCEPTION_METHOD_CAUSE     VARCHAR(255)
	,EXCEPTION_METHOD_INFO      VARCHAR(255)
	,EXCEPTION_LOG_MSG          TEXT
	,LOG_DATE                   TIMESTAMP
	,PRIMARY KEY (ERROR_LOGGING_ID)
);

DROP TABLE IF EXISTS chmm_board_image_map;
CREATE TABLE chmm_board_image_map
(
	board_id  BIGINT       NOT NULL,
	image_url VARCHAR(255) NOT NULL,
	PRIMARY KEY (board_id, image_url)
);

DROP TABLE IF EXISTS chmm_menu_component_info;
create table chmm_menu_component_info
(
	menu_id				varchar(255) not null,
	component_id		varchar(255) not null,
	use_yn				CHAR(1),
	component_name		varchar(255),
	component_desc		varchar(1000),
	component_type		integer not null,
	component_url		varchar(255),
	sys_insert_dtm 		timestamp NOT NULL DEFAULT '1970-01-01 00:00:00',
	sys_insert_user_id	varchar(255) DEFAULT NULL,
	sys_update_dtm		timestamp,
	sys_update_user_id	varchar(255),
	PRIMARY KEY (menu_id, component_id)
);

DROP TABLE IF EXISTS chmm_menu_component_role_map;
CREATE TABLE chmm_menu_component_role_map (
	menu_id VARCHAR(255) NOT NULL,
	component_id VARCHAR(255) NOT NULL,
	role_id VARCHAR(255) NOT NULL,
	use_yn CHAR(1),
	sys_insert_dtm TIMESTAMP NOT NULL DEFAULT '1970-01-01 00:00:00',
	PRIMARY KEY (menu_id, component_id, role_id)
);

DROP TABLE IF EXISTS chmm_user_access_logging;
CREATE TABLE chmm_user_access_logging (
	USER_ACCESS_LOGGING_ID BIGSERIAL NOT NULL,
	CLIENT_ID varchar(30) DEFAULT NULL,
	CLIENT_IP varchar(50) DEFAULT NULL,
	REQUEST_URL varchar(255) DEFAULT NULL,
	REQUEST_ID varchar(20) DEFAULT NULL,
	SESSION_ID varchar(20) DEFAULT NULL,
	LOGIN_USER_ID varchar(30) DEFAULT NULL,
	USER_ACCESS_ACTION_TYPE varchar(20) DEFAULT NULL,
	LOG_DATE timestamp NOT NULL,
PRIMARY KEY (USER_ACCESS_LOGGING_ID)
);

DROP TABLE IF EXISTS CHMM_APP_DEVICE_MAP;
CREATE TABLE CHMM_APP_DEVICE_MAP (
	 DEVICE_ID VARCHAR(256) NOT NULL,
	 LAST_LOGIN_DATE TIMESTAMP DEFAULT NULL,
	 APP_ID VARCHAR(64) NOT NULL,
	 APP_VER VARCHAR(32) NOT NULL,
	 OS_TYPE VARCHAR(32) NOT NULL,
	 PRIMARY KEY (DEVICE_ID,APP_ID,APP_VER,OS_TYPE)
);

DROP TABLE IF EXISTS CHMM_APP_STORE_MAP;
CREATE TABLE CHMM_APP_STORE_MAP (
	APP_ID VARCHAR(64) NOT NULL,
	APPSTORE_CODE VARCHAR(100) NOT NULL,
	PRIMARY KEY (APP_ID,APPSTORE_CODE)
);

DROP TABLE IF EXISTS CHMM_APP_USER_MAP;
CREATE TABLE CHMM_APP_USER_MAP (
   USER_ID VARCHAR(255) NOT NULL,
   APP_ID VARCHAR(64) NOT NULL,
   PRIMARY KEY (APP_ID,USER_ID)
);

DROP TABLE IF EXISTS CHMM_APP_FILE_INFO;
CREATE TABLE CHMM_APP_FILE_INFO (
	APP_ID VARCHAR(64) NOT NULL,
	OS_VER_MIN VARCHAR(32) NOT NULL,
	OS_VER_MAX VARCHAR(32) NOT NULL,
	FILE_DESC VARCHAR(2000),
	APP_FILE_NAME VARCHAR(256) NOT NULL,
	APP_HASH_VALUE VARCHAR(256),
	PLIST_FILE_NAME VARCHAR(256),
	DOWNLOAD_CNT INT NOT NULL DEFAULT 0,
	DEPLOY_PHASE VARCHAR(5) NOT NULL DEFAULT 'WAIT',
	UPLOAD_APP_FILE_CODE VARCHAR(100),
	UPLOAD_PLIST_FILE_CODE VARCHAR(100),
	UPLOAD_LOGO_FILE_CODE VARCHAR(100),
	REQUIRED_UPDATES CHAR(1) DEFAULT '0',
	APP_VER VARCHAR(32) NOT NULL,
	OS_TYPE VARCHAR(32) NOT NULL,
	SYS_INSERT_DTM TIMESTAMP DEFAULT NULL,
	SYS_INSERT_USER_ID VARCHAR(255) DEFAULT NULL,
	SYS_UPDATE_DTM TIMESTAMP DEFAULT NULL,
	SYS_UPDATE_USER_ID VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (APP_ID, APP_VER, OS_TYPE)
);

DROP TABLE IF EXISTS CHMM_APP_DEVICE_INFO;
CREATE TABLE CHMM_APP_DEVICE_INFO (
  DEVICE_ID VARCHAR(256) NOT NULL,
  USER_ID VARCHAR(255) NOT NULL,
  PHONE_NO VARCHAR(32) DEFAULT NULL,
  DEVICE_NAME VARCHAR(128) DEFAULT NULL,
  OS_TYPE VARCHAR(32) NOT NULL,
  OS_VER VARCHAR(8) NOT NULL,
  DEVICE_TYPE VARCHAR(12) NOT NULL DEFAULT 'PHONE',
  LOSS_YN CHAR(1) NOT NULL DEFAULT '0',
  PUBLIC_YN CHAR(1) NOT NULL DEFAULT '0',
  APRVL_PHASE VARCHAR(10) DEFAULT NULL,
  USE_YN CHAR(1) DEFAULT NULL,
  SYS_INSERT_DTM TIMESTAMP DEFAULT NULL,
  SYS_INSERT_USER_ID VARCHAR(255) DEFAULT NULL,
  SYS_UPDATE_DTM TIMESTAMP DEFAULT NULL,
  SYS_UPDATE_USER_ID VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (DEVICE_ID)
);

DROP TABLE IF EXISTS CHMM_APP_INFO;
CREATE TABLE CHMM_APP_INFO (
   APP_ID VARCHAR(64) NOT NULL,
   APP_NAME VARCHAR(128) NOT NULL,
   APP_DESC VARCHAR(2000) DEFAULT NULL,
   USE_YN CHAR(1) NOT NULL DEFAULT '1',
   DEVICE_AUTO_APRVL_YN CHAR(1) DEFAULT '0',
   CUSTOM_DEVICE_CERT_YN CHAR(1) NOT NULL DEFAULT '0',
   AND_PACKAGE_NAME VARCHAR(128) DEFAULT NULL,
   IOS_IDENTIFIER_NAME VARCHAR(128) DEFAULT NULL,
   APP_DEPLOY_TYPE VARCHAR(3) NOT NULL DEFAULT 'B2B',
   ENCRYPT_YN CHAR(1) DEFAULT '0',
   HASH_CHECK_YN CHAR(1) DEFAULT '0',
   SYS_INSERT_DTM TIMESTAMP DEFAULT NULL,
   SYS_INSERT_USER_ID VARCHAR(255) DEFAULT NULL,
   SYS_UPDATE_DTM TIMESTAMP DEFAULT NULL,
   SYS_UPDATE_USER_ID VARCHAR(255) DEFAULT NULL,
   PRIMARY KEY (APP_ID)
);

DROP TABLE IF EXISTS CHMM_APPSTORE_INFO;
CREATE TABLE CHMM_APPSTORE_INFO (
	APPSTORE_CODE VARCHAR(100) NOT NULL,
	DESCRIPTION VARCHAR(500) DEFAULT NULL,
	SYS_INSERT_DTM TIMESTAMP DEFAULT NULL,
	SYS_INSERT_USER_ID VARCHAR(255) DEFAULT NULL,
	SYS_UPDATE_DTM TIMESTAMP DEFAULT NULL,
	SYS_UPDATE_USER_ID VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (APPSTORE_CODE)
);

DROP TABLE IF EXISTS CHMM_PRIVACY_POLICY_INFO;
CREATE TABLE CHMM_PRIVACY_POLICY_INFO (
	POLICY_ID bigint NOT NULL,
	POLICY_VERSION varchar(10) NOT NULL,
	POLICY_NOTICE_DT varchar(10) DEFAULT NULL,
	POLICY_START_DT varchar(10) DEFAULT NULL,
	POST_YN char(1) NOT NULL DEFAULT '0',
	SYS_INSERT_DTM timestamp NOT NULL,
	SYS_INSERT_USER_ID varchar(255) DEFAULT NULL,
	SYS_UPDATE_DTM timestamp DEFAULT NULL,
	SYS_UPDATE_USER_ID varchar(255) DEFAULT NULL,
	PRIMARY KEY (POLICY_ID),
	UNIQUE (POLICY_VERSION)
);

DROP TABLE IF EXISTS CHMM_PRIVACY_POLICY_SUB_INFO;
CREATE TABLE CHMM_PRIVACY_POLICY_SUB_INFO (
	POLICY_ID bigint NOT NULL,
	POLICY_SUB_VERSION bigint NOT NULL,
	TITLE varchar(255) NOT NULL,
	CONTENT text NOT NULL,
	APPLY_YN char(1) NOT NULL DEFAULT '0',
	SYS_INSERT_DTM timestamp NOT NULL,
	SYS_INSERT_USER_ID varchar(255) DEFAULT NULL,
	SYS_UPDATE_DTM timestamp DEFAULT NULL,
	SYS_UPDATE_USER_ID varchar(255) DEFAULT NULL,
	PRIMARY KEY (POLICY_ID,POLICY_SUB_VERSION)
);

DROP TABLE IF EXISTS CHMM_USER_PREVIOUS_PASSWORDS;
CREATE TABLE CHMM_USER_PREVIOUS_PASSWORDS (
	PREVIOUS_PASSWORDS_ID SERIAL PRIMARY KEY,
	USER_ID VARCHAR(100) NOT NULL,
	PASSWORD_HASH VARCHAR(200) NOT NULL,
	CHANGED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (USER_ID, PASSWORD_HASH)
);

DROP TABLE IF EXISTS REVINFO;
CREATE TABLE REVINFO
(
	REV      		BIGSERIAL PRIMARY KEY
	,REVTSTMP 		BIGINT
	,CLIENT_IP 		VARCHAR(50)
	,USER_ID  		VARCHAR(255)
);

DROP TABLE IF EXISTS CHMM_USER_INFO_HISTORY;
CREATE TABLE CHMM_USER_INFO_HISTORY
(
	USER_ID                 VARCHAR(255)
	,REV_ID                 INT          	NOT NULL
	,REVTYPE                SMALLINT
	,SYS_INSERT_DTM         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
	,SYS_INSERT_USER_ID     VARCHAR(255)    DEFAULT 'SYSTEM'
	,SYS_UPDATE_DTM         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
	,SYS_UPDATE_USER_ID     VARCHAR(255)    DEFAULT 'SYSTEM'
	,ACCOUNT_END_DT         VARCHAR(8)      DEFAULT '99991231'
	,PASSWORD_LOCK_CNT      INT             DEFAULT 0
	,ACCOUNT_NON_LOCK       CHAR(1)         DEFAULT '1'
	,ACCOUNT_START_DT       VARCHAR(8)      DEFAULT '00010101'
	,PASSWORD_EXPIRE_DT     VARCHAR(8)      DEFAULT '99991231'
	,USER_DESC              VARCHAR(1000)
	,USER_EMAIL             VARCHAR(255)
	,USE_YN                 CHAR(1)         DEFAULT '1'
	,EXCEPTION_SEND_YN      CHAR(1)
	,LOG_SEND_YN            CHAR(1)
	,USER_IMG               VARCHAR(4000)
	,USER_MSG               VARCHAR(4000)
	,USER_NAME              VARCHAR(255)
	,USER_NICK              VARCHAR(255)
	,USER_PWD               VARCHAR(255)
	,USER_MOBILE            VARCHAR(14)
	,USER_SNSID             VARCHAR(255)
	,USER_STAT_CD           VARCHAR(16)
	,PRIMARY KEY (REV_ID)
);

DROP TABLE IF EXISTS CHMM_USER_ROLE_MAP_HISTORY;
CREATE TABLE CHMM_USER_ROLE_MAP_HISTORY
(
	ROLE_ID                VARCHAR(255)
	,USER_ID                VARCHAR(255)
	,REV_ID                 INT          	NOT NULL
	,REVTYPE                SMALLINT
	,SYS_INSERT_DTM         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
	,SYS_INSERT_USER_ID     VARCHAR(255)    DEFAULT 'SYSTEM'
	,SYS_UPDATE_DTM         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
	,SYS_UPDATE_USER_ID     VARCHAR(255)    DEFAULT 'SYSTEM'
	,USE_YN                 CHAR(1)
	,PRIMARY KEY (REV_ID)
);

DROP TABLE IF EXISTS CHMM_WITHDRAWN_USERS;
CREATE TABLE CHMM_WITHDRAWN_USERS (
	USER_ID 					VARCHAR(255) NOT NULL,
	WITHDRAWAL_DATE 			TIMESTAMP NOT NULL,
	PRIMARY KEY (USER_ID)
);