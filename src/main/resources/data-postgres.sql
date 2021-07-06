INSERT INTO auth_role (id, name) VALUES ('1', 'NISTAGRAM_USER_ROLE');

INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('1','luka', true, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('2','vlado', false, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('3','vidoje', true, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('4','milica', true, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('5','duja', false, true);
INSERT INTO nistagram_user(id, username, profile_private, messages_enabled) VALUES ('6','kobra', true, false);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO user_role (user_id, role_id) VALUES (6, 1);

INSERT INTO conversation (id) VALUES (1);
INSERT INTO conversation (id) VALUES (2);
INSERT INTO conversation (id) VALUES (3);

INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (1, 0, 1);
INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (1, 3, 2);
INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (2, 0, 1);
INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (2, 3, 4);
INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (2, 1, 5);
INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (3, 3, 2);
INSERT INTO conversation_user (conversation_id, status, user_id) VALUES (3, 0, 6);

INSERT INTO message (message_type, id, sent_at, text, user_id, conversation_id)
    VALUES ('TEXT_MESSAGE', 1, '2021-07-05 21:58:58.508-07', 'Pozdrav!', 1, 1);
INSERT INTO message (message_type, id, sent_at, text, user_id, conversation_id)
    VALUES ('TEXT_MESSAGE', 2, '2021-07-05 21:58:59.508-07', 'Pozdrav, Lule!', 2, 1);
INSERT INTO message (message_type, id, sent_at, text, user_id, conversation_id)
    VALUES ('TEXT_MESSAGE', 3, '2021-07-05 21:59:00.508-07', 'Kako si?', 1, 1);
INSERT INTO message (message_type, id, sent_at, text, user_id, conversation_id)
    VALUES ('TEXT_MESSAGE', 4, '2021-07-05 21:56:00.508-07', 'Smarachyyyy', 5, 2);
INSERT INTO message (message_type, id, sent_at, text, user_id, conversation_id)
    VALUES ('TEXT_MESSAGE', 5, '2021-07-05 21:57:00.508-07', 'Dyyyyyy', 4, 2);

INSERT INTO message(message_type, id, sent_at, content_id, user_id, conversation_id)
	VALUES ('CONTENT_MESSAGE', 20, '2021-07-05 21:58:58.508-07', 1, 2, 1);
INSERT INTO message(message_type, id, sent_at, content_id, user_id, conversation_id)
	VALUES ('CONTENT_MESSAGE', 21, '2021-07-05 21:58:58.508-07', 1, 1, 2);
	INSERT INTO message(message_type, id, sent_at, content_id, user_id, conversation_id)
	VALUES ('CONTENT_MESSAGE', 22, '2021-07-05 22:00:00.508-07', 7, 1, 1);