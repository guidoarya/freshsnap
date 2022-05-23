import References from "../models/referenceModel.js";
import { Sequelize } from "sequelize";
import fs from "fs";
import path from "path";

export const getReference = async (req, res) => {
  try {
    const item = await Items.findAll({
      attributes: ["id", "name", "type", "image", "howtokeep", "reference"],
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
    await Items.create({
      name: name,
      type: type,
      image: upload,
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
  const findItem = await Items.findOne({
    where: {
      id: req.params.id,
    },
  });

  if (!findItem) {
    return res.send("Item is not found!");
  }

  try {
    await fs.unlinkSync(path.join(`public/${findItem.image}`));
    await Items.destroy({
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
