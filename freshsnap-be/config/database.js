import { Sequelize } from "sequelize";

const db = new Sequelize("freshsnap", "root", "", {
  host: "localhost",
  dialect: "mysql",
});

export default db;
