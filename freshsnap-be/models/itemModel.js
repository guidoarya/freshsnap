import { Sequelize } from "sequelize";
import db from "../config/database.js";

const { DataTypes } = Sequelize;

const Item = db.define(
  "item",
  {
    name: {
      type: DataTypes.STRING,
    },
    type: {
      type: DataTypes.STRING,
    },
    dummy_picture: {
      type: DataTypes.STRING,
    },
    howtokeep: {
      type: DataTypes.STRING,
    },
    reference: {
      type: DataTypes.STRING,
    },
  },
  {
    freezeTableName: true,
  }
);

export default Item;
