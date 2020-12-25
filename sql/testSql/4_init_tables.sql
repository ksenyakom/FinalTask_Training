USE `test_yoga_db`;

INSERT INTO `user` (
	`id`,
	`login`,
	`password`,
	`role`
) VALUES (
	1,
	"admin",
	"$2a$10$zu5PNHX8IGU6y1J4vLHJReq2vsWGmAAzy.TnohZxVD.Feo5.3nMZC", /* bcrypt хэш пароля "admin" */
	0
);