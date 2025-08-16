-- 사용자 정보 테이블 데이터
INSERT INTO CHMM_USER_INFO (USER_ID, USER_EMAIL, USER_MOBILE, USER_NAME, USER_NICK, USER_PWD, USER_IMG, USER_MSG, USER_DESC, USER_STAT_CD, USER_SNSID, ACCOUNT_NON_LOCK, ACCOUNT_START_DT, ACCOUNT_END_DT, PASSWORD_EXPIRE_DT, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID, PASSWORD_LOCK_CNT, EXCEPTION_SEND_YN, LOG_SEND_YN)
VALUES
    ('chmm23', 'admin@chamomile.net', '010-1234-5678', '어드민', '', '27d14effa31a772b2ee217b8c1b3a025fd9f888cb08fbf00dbc4970700b2dbe4ff501e29c7b853ab', NULL, '', '', 'business trip', NULL, '1', '20000101', '21001231', '21001231', '1', NULL, NULL, NULL, NULL, 0, '1', '0'),
	('user', 'user@chamomile.net', '010-1234-5678', '유저', '', '27d14effa31a772b2ee217b8c1b3a025fd9f888cb08fbf00dbc4970700b2dbe4ff501e29c7b853ab', NULL, '', '', 'business trip', NULL, '1', '20000101', '21001231', '21001231', '1', NULL, NULL, NULL, NULL, 0, '1', '0');

-- 사용자 권한 매핑 테이블 데이터
INSERT INTO CHMM_USER_ROLE_MAP (USER_ID, ROLE_ID, USE_YN, SYS_INSERT_DTM)
VALUES
    ('chmm23', 'ROLE_ADMIN_ID', '1', NULL),
 	('chmm23', 'ROLE_DEFAULT_ID', '1', NULL),
 	('user', 'ROLE_USER_ID', '1', NULL);

-- 권한 상하 관계 테이블 데이터
INSERT INTO CHMM_MULTI_ROLE_GROUP_INFO (PARENT_ROLE_ID, CHILD_ROLE_ID) VALUES
    ('ROLE_ADMIN_ID', 'ROLE_USER_ID'),
    ('ROLE_USER_ID', 'ROLE_DEFAULT_ID');

-- 리소스 테이블 데이터
INSERT INTO CHMM_RESOURCE_INFO (RESOURCE_ID, RESOURCE_URI, RESOURCE_NAME, RESOURCE_HTTPMETHOD, RESOURCE_DESC, SECURITY_ORDER, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
VALUES
    ('RESOURCE_ADMIN_ID', '/admin/**', '어드민 리소스', NULL, '어드민 리소스 입니다.', 1000, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
	('RESOURCE_DEFAULT_ID', '/**', '기본 리소스', NULL, '기본 리소스 입니다.', 99999, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
	('리소스', '/**', '한글 리소스', NULL, '한글 리소스 입니다.', 99998, '1', '2023-09-18 09:00:00', 'chmm23', '2023-09-18 09:00:00', 'chmm23');

-- 권한 테이블 데이터
INSERT INTO CHMM_ROLE_INFO (ROLE_ID, ROLE_NAME, ROLE_DESC, ROLE_START_DT, ROLE_END_DT, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'ROLE_ADMIN_ID', '어드민 권한', '어드민 권한 입니다.', '20180701', '21001231', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ROLE_USER_ID', '사용자 권한', '사용자 권한 입니다.', '20180701', '21001231', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ROLE_DEFAULT_ID', '기본 권한', '기본 권한 입니다.', '20180701', '21001231', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system';

-- 리소스 권한 매핑 테이블 데이터
INSERT INTO CHMM_RESOURCE_ROLE_MAP (RESOURCE_ID, ROLE_ID, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID)
SELECT 'RESOURCE_ADMIN_ID', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'RESOURCE_DEFAULT_ID', 'ROLE_DEFAULT_ID', '1', CURRENT_TIMESTAMP, 'system';

-- 권한 메뉴 매핑 테이블 데이터
INSERT INTO CHMM_ROLE_MENU_MAP (MENU_ID, ROLE_ID, USE_YN, SYS_INSERT_DTM)
SELECT 'menu00000001', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP UNION ALL
SELECT 'menu00000002', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP UNION ALL
SELECT 'menu00000003', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP UNION ALL
SELECT 'menu00000004', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP UNION ALL
SELECT 'menu00000101', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP;

-- 시스템 환경관리 테이블 데이터
INSERT INTO CHMM_SYSTEM_DEFAULT_INFO (ENV_ID, ENV_VALUE, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'EXCEPTION_LOG_GN', '0', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'EXCEPTION_SEND_YN', '1', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'FILEUPLOAD_ALLOWED_EXTENSION', '', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'FILEUPLOAD_MAX_SIZE', '100000000', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'PASSWORD_LOCK_CNT_LIMIT', '10', NULL, NULL;

-- 프로퍼티 관리 테이블 데이터
INSERT INTO CHMM_PROPERTY_INFO  (PROPERTY_KEY, PROPERTY_VALUE) VALUES ('AOPLOGONOFF','0');
INSERT INTO CHMM_PROPERTY_INFO  (PROPERTY_KEY, PROPERTY_VALUE) VALUES ('TRANSLOGONOFF','false');
INSERT INTO CHMM_PROPERTY_INFO  (PROPERTY_KEY, PROPERTY_VALUE) VALUES ('LOGMASKOPT','DB');
INSERT INTO CHMM_PROPERTY_INFO  (PROPERTY_KEY, PROPERTY_VALUE) VALUES ('CM_BASE_HOME','/root/mobile_home');
INSERT INTO CHMM_PROPERTY_INFO  (PROPERTY_KEY, PROPERTY_VALUE) VALUES ('CM_CUSTOM_AUTH_YN','N');

-- 국가코드 테이블 데이터
INSERT INTO CHMM_LANGUAGE_INFO (LANGUAGE_CODE, COUNTRY_CODE, DESCRIPTION, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'en', 'US', 'English(US)', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', '한국어', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system';


-- 공통코드 카테고리 테이블 데이터
INSERT INTO CHMM_CATEGORY_INFO (CATEGORY_ID, CATEGORY_DESC, ORDER_NUM, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID, REAL_VALUE)
SELECT 'category00028', '페이지번호목록', 7, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00030', '공통코드', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00031', '사용자상태', 3, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00036', '상하관계', 6, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00044', '로그설정관리', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00045', '예외설정상태', 2, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00046', '로그마스킹 패턴관리', 4, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'category00047', '로그마스킹 패턴타입', 5, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'category00052', 'MVC', 13, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'G1', '공통', 12, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL;



-- 공통코드 코드 테이블 데이터
INSERT INTO CHMM_CODE_INFO (CATEGORY_ID, CODE_ID, CODE_DESC, ORDER_NUM, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID, REAL_VALUE)
SELECT 'category00028', 'code00001', '페이지목록10', 7, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '10' UNION ALL
SELECT 'category00028', 'code00002', '페이지목록15', 8, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '15' UNION ALL
SELECT 'category00028', 'code00003', '페이지목록20', 9, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '20' UNION ALL
SELECT 'category00028', 'code00004', '페이지목록50', 10, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '50' UNION ALL
SELECT 'category00028', 'code00005', '페이지목록100', 11, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '100' UNION ALL
SELECT 'category00030', 'code00001', '사용여부', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00031', 'code00001', '사용자상태', 7, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00031', 'code00002', '계정잠김', 6, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00036', 'code00001', '상하관계', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00044', 'code00001', '로그레벨', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'level' UNION ALL
SELECT 'category00045', 'code00001', '예외처리 메일 사용 유무', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00045', 'code00002', '예외처리 메세지 구분', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'category00046', 'code00001', '실행여부', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'enabled' UNION ALL
SELECT 'category00047', 'code00001', '정규표현식 유무', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'regexYn' UNION ALL
SELECT 'category00052', 'code00001', 'Method', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'category00052', 'code00002', 'Request approval', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'category00052', 'code00003', 'ApprovalYN', 2, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'category00052', 'code00004', 'Activation', 3, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '' UNION ALL
SELECT 'G1', 'LEVEL', '레벨', 1000, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL UNION ALL
SELECT 'G1', 'USE', '사용여부', 1000, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', NULL;

-- 공통코드 아이템 테이블 데이터
INSERT INTO CHMM_CODE_ITEM_INFO (CATEGORY_ID, CODE_ID, CODE_ITEM_ID, CODE_ITEM_DESC, ORDER_NUM, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID, REAL_VALUE)
SELECT 'category00030', 'code00001', 'item00001', '사용', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1' UNION ALL
SELECT 'category00030', 'code00001', 'item00002', '미사용', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '0' UNION ALL
SELECT 'category00031', 'code00001', 'item00001', '휴가', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '휴가' UNION ALL
SELECT 'category00031', 'code00001', 'item00002', '업무', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '업무' UNION ALL
SELECT 'category00031', 'code00001', 'item00003', '자리비움', 2, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '자리비움' UNION ALL
SELECT 'category00031', 'code00002', 'item00001', '잠김', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '0' UNION ALL
SELECT 'category00031', 'code00002', 'item00002', '잠김해제', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1' UNION ALL
SELECT 'category00036', 'code00001', 'item00001', '상위 ID', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '상위 ID' UNION ALL
SELECT 'category00036', 'code00001', 'item00002', '하위 ID', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '하위 ID' UNION ALL
SELECT 'category00044', 'code00001', 'item00001', 'ALL', 7, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'ALL' UNION ALL
SELECT 'category00044', 'code00001', 'item00002', 'TRACE', 8, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'TRACE' UNION ALL
SELECT 'category00044', 'code00001', 'item00003', 'DEBUG', 9, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'DEBUG' UNION ALL
SELECT 'category00044', 'code00001', 'item00004', 'WARN', 10, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'WARN' UNION ALL
SELECT 'category00044', 'code00001', 'item00005', 'INFO', 11, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'INFO' UNION ALL
SELECT 'category00044', 'code00001', 'item00006', 'ERROR', 12, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'ERROR' UNION ALL
SELECT 'category00044', 'code00001', 'item00007', 'FATAL', 13, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'FATAL' UNION ALL
SELECT 'category00044', 'code00001', 'item00008', 'OFF', 14, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'OFF' UNION ALL
SELECT 'category00045', 'code00001', 'item00001', '아니요', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '0' UNION ALL
SELECT 'category00045', 'code00001', 'item00002', '예', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1' UNION ALL
SELECT 'category00045', 'code00002', 'item00001', '개발자', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '0' UNION ALL
SELECT 'category00045', 'code00002', 'item00002', '사용자', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1' UNION ALL
SELECT 'category00046', 'code00001', 'item00001', 'true', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'true' UNION ALL
SELECT 'category00046', 'code00001', 'item00002', 'false', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'false' UNION ALL
SELECT 'category00047', 'code00001', 'item00001', 'Y', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'Y' UNION ALL
SELECT 'category00047', 'code00001', 'item00002', 'N', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'N' UNION ALL
SELECT 'category00052', 'code00001', 'item00001', 'GET', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'GET' UNION ALL
SELECT 'category00052', 'code00001', 'item00002', 'POST', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'POST' UNION ALL
SELECT 'category00052', 'code00001', 'item00003', 'PUT', 2, '0', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'PUT' UNION ALL
SELECT 'category00052', 'code00001', 'item00004', 'DELETE', 3, '0', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'DELETE' UNION ALL
SELECT 'category00052', 'code00002', 'item00001', '요청', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'true' UNION ALL
SELECT 'category00052', 'code00002', 'item00002', '없음', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'false' UNION ALL
SELECT 'category00052', 'code00003', 'item00001', '승인', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'true' UNION ALL
SELECT 'category00052', 'code00003', 'item00002', '미승인', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'false' UNION ALL
SELECT 'category00052', 'code00004', 'item00001', '활성', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'true' UNION ALL
SELECT 'category00052', 'code00004', 'item00002', '비활성', 1, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', 'false' UNION ALL
SELECT 'G1', 'LEVEL', '0', '대메뉴', 0, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '0' UNION ALL
SELECT 'G1', 'LEVEL', '1', '레벨1', 10, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1' UNION ALL
SELECT 'G1', 'LEVEL', '2', '레벨2', 20, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '2' UNION ALL
SELECT 'G1', 'LEVEL', '3', '레벨3', 30, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '3' UNION ALL
SELECT 'G1', 'LEVEL', '4', '레벨4', 40, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '4' UNION ALL
SELECT 'G1', 'LEVEL', '5', '레벨5', 50, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '5' UNION ALL
SELECT 'G1', 'LEVEL', '6', '레벨6', 60, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '6' UNION ALL
SELECT 'G1', 'USE', '0', '미사용', 12, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '0' UNION ALL
SELECT 'G1', 'USE', '1', '사용', 11, '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1';



-- 메뉴 테이블 데이터
INSERT INTO CHMM_MENU_INFO (MENU_ID, MENU_LVL, MENU_URI, MENU_NAME, UPPER_MENU_ID, MENU_DESC, MENU_SEQ, LEFT_MENU_YN, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID, ADMIN_MENU_YN, MENU_HELP_URI, MENU_SCRIPT)
SELECT 'menu00000001', 0, NULL, '자원관리', 'root', NULL, 10, '1', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1', NULL, '<i class=''fa fa-cubes icon''><b class=''bg-primary dker''></b></i>' UNION ALL
SELECT 'menu00000002', 1, '/admin/resources/user/managing-user', '사용자관리', 'menu00000001', '사용자관리', 10, '1', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1', NULL, NULL UNION ALL
SELECT 'menu00000003', 1, '/admin/resources/userGroup/ManagingGroup', '사용자그룹관리', 'menu00000001', '사용자그룹관리', 20, '1', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1', NULL, NULL UNION ALL
SELECT 'root', NULL, NULL, '메뉴', '#', '메뉴', 1, '1', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', '1', NULL, NULL;

-- 로그마스킹 패턴 관리 테이블 데이터
INSERT INTO CHMM_MASK_PATTERN_INFO (PATTERN_ID, PATTERN_NAME, CLASS, PATTERN, REPLACES, ENABLED, REGDATE, REGEX_YN)
SELECT 'pattern00000001', 'Email', NULL, '[A-Z0-9._%+-]+@([A-Z0-9.-]+).([A-Z]{2,4})', 'xxxx@xxxx.xxx', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000002', 'MasterCard', NULL, '5[1-5][0-9]{2}(\ |\-|)[0-9]{4}(\ |\-|)[0-9]{4}(\D|$)', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000003', 'Visa', NULL, '4[0-9]{3}(\ |\-|)[0-9]{4}(\ |\-|)[0-9]{4}(\ |\-|)[0-9]{4}(\D|$)', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000004', 'KoreaCard', NULL, '5[1-5][0-9]{2}(\ |\-|)[0-9]{4}(\ |\-|)[0-9]{4}(\D|$)', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000005', 'AMEXCard', NULL, '(34|37)[0-9]{2}(\ |\-|)[0-9]{6}(\ |\-|)[0-9]{4}(\D|$)', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000006', 'DinnerClub1', NULL, '30[0-5][0-9](\ |\-|)[0-9]{6}(\ |\-|)[0-9]{4}(\D|$)', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000007', 'DinnerClub2', NULL, '(36|38)[0-9]{2}(\ |\-|)[0-9]{6}(\ |\-|)[0-9]{4}(\D|$)', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000008', 'CreditCard', NULL, '[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000009', 'CreditCard2', NULL, '[0-9]{4}[0-9]{4}[0-9]{4}[0-9]{4}', 'XXXX-XXXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000010', 'IPV4', NULL, '(?:[0-9]{1,3}.){3}[0-9]{1,3}', 'XXX.XXX.XXX.XXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000011', 'USPhone', NULL, '[().]*[0-9]{3}[) -.]{1,2}[0-9]{3}[- .][0-9]{4}', 'XXX-XXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000012', 'URL', NULL, '((https?|ftp|file)://|(www|ftp)\.)[A-Z0-9+&@#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|]', 'http://xxx.xxxxx.xxx', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000013', 'UKPhone', NULL, '^(((?0d{4})?s?d{3})|((?0d{3})?s?d{4})|((?0d{2})?s?d{4}s?d{4}))(s?#(d{4}|d{3}))?$', 'XXX-XXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000014', 'Passport', NULL, '[a-zA-Z]{1}[0-9a-zA-Z]{1}[0-9]{7}', 'XXXXXXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000015', 'KRPhone1', NULL, '^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$', 'XXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000016', 'KRPhone2', NULL, '^d{2,3}-d{3,4}-d{4}$', 'XXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000017', 'KRPhone3', NULL, '^d{3}-d{3,4}-d{4}$', 'XXX-XXXX-XXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000018', 'PostCode', NULL, '[0-9]{3}-[0-9]{3}$', 'XXX-XXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000019', 'JuminNumber', NULL, '^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$', 'XXXXXX-XXXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000020', 'KoreaPassport', NULL, '/b^(A-PR-WYa-pr-wy)[1-9]ds?d{4}[1-9]$/b', 'XXXXXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000021', 'BankShinHanOld', NULL, '^d{3}+-?d{2}+-?d{5}+$', 'XXX-XX-XXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000022', 'BankShinHan', NULL, '^d{3}+-?d{3}+-?d{5}+$', 'XXX-XXX-XXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000023', 'BankKookmin', NULL, '^d{6}+-?d{2}+-?d{6}+$', 'XXXXXX-XX-XXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000024', 'BankWoori', NULL, '/b[0-9]{4}-[0-9]{3}-[0-9]{6}$/b', 'XXXX-XXX-XXXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000025', 'BankIBK', NULL, '^d{3}+-?d{6}+-?d{2}+-?d{3}+$', 'XXX-XXXXXX-XX-XXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000026', 'BankHana', NULL, '^d{3}+-?d{6}+-?d{5}+$', 'XXX-XXXXXX-XXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000027', 'BankDaegu', NULL, '^d{3}+-?d{2}+-?d{6}+-?d{1}+$', 'XXX-XX-XXXXXX-X', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000028', 'BankDaeguNoPay', NULL, '^d{3}+-?d{2}+-?d{5}+$', 'XXX-XX-XXXXX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000029', 'BankPusan', NULL, '^d{3}+-?d{4}+-?d{4}+-?d{2}+$', 'XXX-XXXX-XXXX-XX', 'false', NOW(), 'Y' UNION ALL
SELECT 'pattern00000030', 'id', NULL, 'id', NULL, 'false', NOW(), 'N' UNION ALL
SELECT 'pattern00000031', 'pwd', NULL, 'pwd', NULL, 'false', NOW(), 'N' UNION ALL
SELECT 'pattern00000032', 'password', NULL, 'password', NULL, 'false', NOW(), 'N' UNION ALL
SELECT 'pattern00000033', 'pass', NULL, 'pass', NULL, 'false', NOW(), 'N' UNION ALL
SELECT 'pattern00000034', '아이디', NULL, '아이디', NULL, 'false', NOW(), 'N' UNION ALL
SELECT 'pattern00000035', '비밀번호', NULL, '비밀번호', NULL, 'false', NOW(), 'N';

-- REST 트리 데이터
INSERT INTO CHMM_REST_TREE_INFO(SERVICE_ID, SERVICE_NAME, SERVICE_ICON, UPPER_FOLDER_ID, UPPER_FOLDER_IDS, USE_YN)
SELECT 'root0', 'ROOT', 'root', '#', '#', '1' UNION ALL
SELECT 'Scenario0', 'scenario1', 'folder', 'root0', 'root0,#', '1' UNION ALL
SELECT 'TestCase0', 'testCase1', 'file', 'Scenario0', 'Scenario0,root0,#', '1';

-- 기본 데이터 (실제로는 ROOT 만 기본으로 생성될 예정)
INSERT INTO CHMM_PERFORM_TREE_INFO(SERVICE_ID, SERVICE_NAME, SERVICE_ICON, UPPER_FOLDER_ID, UPPER_FOLDER_IDS, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'root0', 'ROOT', 'root', '#', '#', '1', null, null, null, null;

-- REST 스케쥴 데이터
INSERT INTO CHMM_REST_SCHEDULE_INFO(SCHEDULE_ID, CRON_EXPR, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'restSchedule1', '0 0 0 * * ?', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'performanceSchedule1', '0 0 1 * * ?', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system';

-- 그룹 데이터
INSERT INTO CHMM_GROUP_INFO(GROUP_ID, GROUP_NAME, GROUP_DESC, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
VALUES
    ('group01', '그룹01', '그룹01 설명입니다.', '1', '2023-09-18 09:00:12', NULL, NULL, NULL),
	('group02', '그룹02', '그룹02 설명입니다.', '1', '2023-09-18 09:00:11', NULL, NULL, NULL),
	('group03', '그룹03', '그룹03 설명입니다.', '1', '2023-09-18 09:00:10', NULL, NULL, NULL),
	('group04', '그룹04', '그룹04 설명입니다.', '1', '2023-09-18 09:00:09', NULL, NULL, NULL),
	('group05', '그룹05', '그룹05 설명입니다.', '1', '2023-09-18 09:00:08', NULL, NULL, NULL),
	('group06', '그룹06', '그룹06 설명입니다.', '1', '2023-09-18 09:00:07', NULL, NULL, NULL),
	('group07', '그룹07', '그룹07 설명입니다.', '1', '2023-09-18 09:00:06', NULL, NULL, NULL),
	('group08', '그룹08', '그룹08 설명입니다.', '1', '2023-09-18 09:00:05', NULL, NULL, NULL),
	('group09', '그룹09', '그룹09 설명입니다.', '1', '2023-09-18 09:00:04', NULL, NULL, NULL),
	('group10', '그룹10', '그룹10 설명입니다.', '1', '2023-09-18 09:00:03', NULL, NULL, NULL),
	('group11', '그룹11', '그룹11 설명입니다.', '1', '2023-09-18 09:00:02', NULL, NULL, NULL),
	('group12', '그룹12', '그룹12 설명입니다.', '1', '2023-09-18 09:00:01', NULL, NULL, NULL);

-- 그룹 유저 데이터
INSERT INTO CHMM_USER_GROUP_MAP(GROUP_ID, USER_ID, SYS_INSERT_DTM, SYS_INSERT_USER_ID, USE_YN)
VALUES
    ('group01', 'user', '2023-09-18 09:00:12', NULL, '1'),
	('group01', 'chmm23', '2023-09-18 09:00:12', NULL, '1'),
	('group02', 'chmm23', '2023-09-18 09:00:11', NULL, '1'),
	('group03', 'chmm23', '2023-09-18 09:00:10', NULL, '1'),
	('group04', 'chmm23', '2023-09-18 09:00:09', NULL, '1'),
	('group05', 'chmm23', '2023-09-18 09:00:08', NULL, '1'),
	('group06', 'chmm23', '2023-09-18 09:00:07', NULL, '1'),
	('group07', 'chmm23', '2023-09-18 09:00:06', NULL, '1'),
	('group08', 'chmm23', '2023-09-18 09:00:05', NULL, '1'),
	('group09', 'chmm23', '2023-09-18 09:00:04', NULL, '1'),
	('group10', 'chmm23', '2023-09-18 09:00:03', NULL, '1'),
	('group11', 'chmm23', '2023-09-18 09:00:02', NULL, '1'),
	('group12', 'chmm23', '2023-09-18 09:00:01', NULL, '1');

-- 그룹 상하 목록 데이터
INSERT INTO CHMM_MULTI_USER_GROUP_INFO(PARENT_GROUP_ID, CHILD_GROUP_ID)
VALUES
	('group04', 'group01'),
    ('group10', 'group11'),
    ('group11', 'group12'),
    ('group10', 'group12');



-- 그룹 권한 매핑 데이터

INSERT INTO CHMM_GROUP_ROLE_MAP(GROUP_ID, ROLE_ID, SYS_INSERT_DTM, SYS_INSERT_USER_ID, USE_YN)
VALUES
	('group01','ROLE_USER_ID','2023-09-18 09:00:00','chmm23','1');


-- 다국어 관리 테이블 데이터
INSERT INTO CHMM_MESSAGE_SOURCE_INFO (LANGUAGE_CODE, COUNTRY_CODE, CODE, MESSAGE, USE_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'en', 'US', 'confirm.httpmethod.alert', 'If you do not specify the Http method, it is treated as [Apply all]. Do you want to proceed?', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'confirm.httpmethod.alert', 'Http method를 지정 하지 않을 경우 [모두 적용하기]로 처리됩니다. 진행 하시겠습니까?', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'common.message.applyAll', 'apply all', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'common.message.applyAll', '모두 적용', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'grid.column.resourceHttpMethod', 'Http Method', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'grid.column.resourceHttpMethod', 'Http Method', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'screen.message.allow.extensions', 'Allow all extensions', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'screen.message.allow.extensions', '모든 확장자 허용', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'grid.columnheader.method.tooltip', 'The methods that are not defined are GET, POST.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'grid.columnheader.method.tooltip', '메소드가 정의되어 있지 않는 것은 GET, POST 이다.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'menu.data.menu00000068', 'Integrated Monitoring', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'menu.data.menu00000068', '통합 모니터링', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'intgMntr.object.server', 'Server', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'intgMntr.object.server', '서버', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'intgMntr.object.java', 'Web/Java', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'intgMntr.object.java', '웹/Java', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'intgMntr.object.dataSource', 'DataSource', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'intgMntr.object.dataSource', '데이터소스', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'intgMntr.object.reqProc', 'ReqProc', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'intgMntr.object.reqProc', 'ReqProc', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'intgMntr.btn.monitoringSettings', 'MonitoringSettings', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'intgMntr.btn.monitoringSettings', '모니터링설정', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'intgMntr.object.scouterConnectFail', 'Scouter Connect Fail', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'intgMntr.object.scouterConnectFail', '스카우터 연결 실패', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.title', 'Service Test', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.title', '서비스 테스트', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.allServices', 'All Services', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.allServices', '서비스 전체 보기', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.header', 'Header', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.header', '헤더', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.body', 'Body', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.body', '바디', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.assertion', 'Assertion', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.assertion', 'Assertion', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.response', 'Response', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.response', '응답', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.serviceList', 'Service List', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.serviceList', '서비스 리스트', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.testCase.alertCheck', 'You must specify a test case to save.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.testCase.alertCheck', '저장할 테스트케이스를 지정해야 합니다.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.testCase.alertCheckOne', 'You must specify only one test case to save.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.testCase.alertCheckOne', '저장할 테스트케이스를 하나만 지정해야 합니다.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.scenario.title', 'Scenario Test', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.scenario.title', '시나리오 테스트', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.schedule.schedule', 'Schedule', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.schedule.schedule', '스케쥴', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.scenarioHistory.title', 'Scenario History', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.scenarioHistory.title', '시나리오 이력', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.serviceTest.title.description', 'Save and recall test cases and for test service.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.serviceTest.title.description', '테스트 케이스 저장 및 불러오기, 서비스 테스트를 위한 화면입니다.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.scenario.title.description', 'Run the scenario and save the history.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.scenario.title.description', '시나리오를 실행하고 이력을 저장합니다.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'en', 'US', 'rest.scenarioHistory.title.description', 'Check scenario history.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'ko', 'KR', 'rest.scenarioHistory.title.description', '시나리오 이력을 확인합니다.', '1', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT  'en', 'US', 'grid.column.sucRate', 'Success rate', '1', null, null, null, null UNION ALL
SELECT  'ko', 'KR', 'grid.column.sucRate', '성공률', '1', null, null, null, null;

INSERT INTO CHMM_MENU_COMPONENT_INFO (MENU_ID, COMPONENT_ID, USE_YN, COMPONENT_NAME, COMPONENT_DESC, COMPONENT_TYPE, COMPONENT_URL, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
SELECT 'menu00000101', 'button1', '1', 'test', 'test', 1, '/test', CURRENT_TIMESTAMP, 'chmm23', CURRENT_TIMESTAMP, 'chmm23' UNION ALL
SELECT 'menu00000101', 'button2', '1', 'test2', 'test2', 1, '/test2', CURRENT_TIMESTAMP, 'chmm23', CURRENT_TIMESTAMP, 'chmm23';

INSERT INTO CHMM_MENU_COMPONENT_ROLE_MAP (MENU_ID, COMPONENT_ID, ROLE_ID, USE_YN, SYS_INSERT_DTM)
SELECT 'menu00000101', 'button1', 'ROLE_ADMIN_ID', '1', CURRENT_TIMESTAMP UNION ALL
SELECT 'menu00000101', 'button1', 'ROLE_USER_ID', '1', CURRENT_TIMESTAMP;

INSERT INTO chmm_user_access_logging(USER_ACCESS_LOGGING_ID, LOGIN_USER_ID, USER_ACCESS_ACTION_TYPE, LOG_DATE)
VALUES ('5', 'chmm23', 'LOGIN_SUCCESS', CURRENT_TIMESTAMP);

INSERT INTO chmm_user_access_logging(USER_ACCESS_LOGGING_ID, LOGIN_USER_ID, USER_ACCESS_ACTION_TYPE, LOG_DATE)
VALUES ('6', 'chmm23', 'LOGIN_SUCCESS', CURRENT_TIMESTAMP);

-- 모바일 앱 매핑 데이터
INSERT INTO CHMM_APP_INFO (APP_ID, APP_NAME, APP_DESC, USE_YN, DEVICE_AUTO_APRVL_YN, APP_DEPLOY_TYPE, SYS_INSERT_USER_ID, SYS_INSERT_DTM, SYS_UPDATE_USER_ID, SYS_UPDATE_DTM)
SELECT 'chamomileappaos', '캐모마일(AOS)', '캐모마일(AOS)', '1', '1', 'B2B', 'chmm23', CURRENT_TIMESTAMP, 'chmm23', CURRENT_TIMESTAMP UNION ALL
SELECT 'chamomileappios', '캐모마일(IOS)', '캐모마일(IOS)', '1', '1', 'B2B', 'chmm23', CURRENT_TIMESTAMP, 'chmm23', CURRENT_TIMESTAMP UNION ALL
SELECT 'chamomile', '캐모마일', '캐모마일', '1', '1', 'B2B', 'chmm23', CURRENT_TIMESTAMP, 'chmm23', CURRENT_TIMESTAMP;

-- 모바일 앱 파일 매핑 데이터
INSERT INTO CHMM_APP_FILE_INFO (APP_ID, APP_VER, OS_TYPE, OS_VER_MIN, OS_VER_MAX, FILE_DESC, APP_FILE_NAME, PLIST_FILE_NAME, DOWNLOAD_CNT, DEPLOY_PHASE, SYS_INSERT_USER_ID, SYS_INSERT_DTM, UPLOAD_APP_FILE_CODE, UPLOAD_PLIST_FILE_CODE, UPLOAD_LOGO_FILE_CODE, REQUIRED_UPDATES)
SELECT 'chamomileappaos', '1.0', 'aos', '27', '30', '', 'ChamomileMobile.apk', '', 0, 'FINAL', 'chmm23', '2024-05-28 00:00:00', '', '', '', '1' UNION ALL
SELECT 'chamomile', '1.0', 'aos', '27', '30', '', 'chamomile_dev_debug_1.0_1.apk', '', 0, 'FINAL', 'chmm23', NOW(), '', NULL, 'd520df36a75f46bc81cfca724905e6aa', '1' UNION ALL
SELECT 'chamomileappios', '1.0', 'ios', '1', '15', '', 'ChamomileMobile.ipa', 'ChamomileMobile.plist', 0, 'FINAL', 'chmm23', NOW(), 'd520df36a75f46bc81cfca724905e6aa', NULL, '', '1' UNION ALL
SELECT 'chamomileappaos', '1.2', 'aos', '1', '30', '', 'ChamomileMobile.apk', '', 0, 'FINAL', 'chmm23', '2024-05-28 00:00:00', '', '', '', '1' UNION ALL
SELECT 'chamomile', '2.0', 'aos', '1', '30', '', 'chamomile.apk', '', 0, 'FINAL', 'chmm23', NOW(), 'da39a80477b347e6a5a712970c3f3e30', NULL, NULL, '1';


INSERT INTO CHMM_FILE_METADATA_INFO (FILE_METADATA_CODE, FILE_UPLOAD_PATH, ORIGINAL_FILE_NAME,
									 UPLOAD_FILE_NAME, FILE_SIZE, SYS_INSERT_DTM, SYS_INSERT_USER_ID, URI)
SELECT 'da39a80477b347e6a5a712970c3f3e30', '/mobile/download/app/chamomile/aos/2.0/', 'chamomile.apk',
        'da39a80477b347e6a5a712970c3f3e30', '49048', NOW(), 'chmm23', '/chmm/admin/mobile/file/upload' UNION ALL
SELECT 'd520df36a75f46bc81cfca724905e6aa', '/mobile/download/app/chamomile/aos/2.0/', 'ChamomileMobile.apk',
	   'd520df36a75f46bc81cfca724905e6aa', '49048', NOW(), 'chmm23', '/chmm/admin/mobile/file/upload';

INSERT INTO CHMM_APP_DEVICE_INFO (DEVICE_ID, DEVICE_NAME, USER_ID, OS_TYPE, OS_VER, DEVICE_TYPE,
                                  APRVL_PHASE, USE_YN, LOSS_YN, PUBLIC_YN, SYS_INSERT_USER_ID,
                                  SYS_INSERT_DTM, SYS_UPDATE_USER_ID, SYS_UPDATE_DTM)
VALUES('1234', NULL, 'admin', 'android', '27', 'PHONE', 'APRVL', '1', '0', '0', '', NOW(), NULL, NOW());

INSERT INTO CHMM_APP_DEVICE_MAP (DEVICE_ID, LAST_LOGIN_DATE, APP_ID, APP_VER, OS_TYPE)
VALUES('1234', NULL, 'chamomile', '2.0', 'aos');

INSERT INTO CHMM_APPSTORE_INFO (appstore_code, description, sys_insert_dtm, sys_insert_user_id,
                                           sys_update_dtm, sys_update_user_id)
SELECT 'delivery', '배송기사용', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system' UNION ALL
SELECT 'user', '일반사용자용', '2024-12-18 05:59:14', 'admin', '2024-12-18 05:59:14', 'admin';

INSERT INTO CHMM_APP_USER_MAP (user_id, app_id)
VALUES('admin', 'chamomile');

INSERT INTO CHMM_APP_STORE_MAP (app_id, appstore_code)
SELECT 'chamomile', 'delivery' UNION ALL
SELECT 'chamomile', 'user';

INSERT INTO CHMM_PRIVACY_POLICY_INFO
(POLICY_ID, POLICY_VERSION, POLICY_NOTICE_DT, POLICY_START_DT, POST_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
VALUES(1, '1.0', '2024-08-20', '2024-08-22', '1', NOW(), 'chmm23', NOW(), 'chmm23');

INSERT INTO CHMM_PRIVACY_POLICY_SUB_INFO
(POLICY_ID, POLICY_SUB_VERSION, TITLE, CONTENT, APPLY_YN, SYS_INSERT_DTM, SYS_INSERT_USER_ID, SYS_UPDATE_DTM, SYS_UPDATE_USER_ID)
VALUES(1, 1, '개인정보 처리방침 1.0', '<h1>내용</h1>', '1', NOW(), 'chmm23', NOW(), 'chmm23');
