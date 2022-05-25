import { Sequelize } from "sequelize";

// const database = "id18989744_freshsnap_name";
// const username = "id18989744_freshsnap_usr";
// const password = "GUIDO123^abc";

const database = "freshsnap";
const username = "root";
const password = "";

const db = new Sequelize(database, username, password, {
  host: "localhost",
  dialect: "mysql",
});

export default db;
