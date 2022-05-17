import Item from "../models/itemModel.js";
import { Sequelize } from "sequelize";
import fs from "fs";
import path from "path";

export const getItem = async (req, res) => {
  try {
    const item = await Item.findAll({
      attributes: ["id", "name", "type", "dummy_picture", "howtokeep", "reference"],
    });
    res.json(item);
  } catch (error) {
    console.log(error);
  }
};

export const addItem = async (req, res) => {
  const { name, type, howtokeep, reference } = req.body;
  console.log(req.file.path);

  if (!req.file) {
    return res.send("Input image tidak adaa!");
  }

  if (!name || !type || !howtokeep || !reference) {
    return res.send("All field must be filled!");
  }

  const upload = "uploads/" + req.file.filename;
  try {
    await Item.create({
      name: name,
      type: type,
      dummy_picture: upload,
      howtokeep: howtokeep,
      reference: reference,
    });
    return res.json({ msg: "New item has been created!" });
  } catch (error) {
    console.log(error);
  }
};

export const deleteItem = async (req, res) => {
  console.log(req.params.id);
  const findItem = await Item.findOne({
    where: {
      id: req.params.id,
    },
  });

  if (!findItem) {
    return res.send("Item is not found!");
  }

  console.log(findItem.dummy_picture);

  try {
    await fs.unlink(path.join(`public/uploads/${findItem.dummy_picture}`));
    await Item.destroy({
      where: {
        id: req.params.id,
      },
    });
    res.json({
      msg: "Item was deleted!",
    });
  } catch (err) {
    console.log({ msg: err.message });
  }
};
