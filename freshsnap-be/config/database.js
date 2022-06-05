import { Sequelize } from "sequelize";

// const database = "freshsnap";
// const username = "root";
// const password = "";
// const host = "localhost";

const host = "35.225.57.110";
const database = "freshsnap";
const username = "root";
const password = "root";

const db = new Sequelize(database, username, password, {
  host: host,
  dialect: "mysql",
});

export default db;
