INSERT INTO auth_role (id, name) VALUES ('1', 'NISTAGRAM_USER_ROLE');

INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('1','luka', true, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('2','vlado', false, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('3','vidoje', true, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('4','milica', true, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('5','duja', false, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('6','kobra', true, true);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO user_role (user_id, role_id) VALUES (6, 1);
