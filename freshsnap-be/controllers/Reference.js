import References from "../models/referenceModel.js";
import { Sequelize } from "sequelize";
import fs from "fs";
import path from "path";

export const getReference = async (req, res) => {
  try {
    const reference = await References.findAll({
      attributes: ["id", "reference_name", "name", "image"],
    });
    res.json(reference);
  } catch (error) {
    console.log(error);
  }
};

export const addReference = async (req, res) => {
  const { reference_name, name } = req.body;
  console.log(req.file.path);

  if (!req.file) {
    return res.send("Input image is not found!");
  }

  if (!name || !reference_name) {
    return res.send("All field must be filled!");
  }

  const upload = "uploads/" + req.file.filename;
  try {
    await References.create({
      reference_name: reference_name,
      name: name,
      image: upload,
    });
    return res.json({ msg: "New reference has been created!" });
  } catch (error) {
    console.log(error);
  }
};

export const deleteReference = async (req, res) => {
  const findReference = await References.findOne({
    where: {
      id: req.params.id,
    },
  });

  if (!findReference) {
    return res.send("References is not found!");
  }

  try {
    await fs.unlinkSync(path.join(`public/${findReference.image}`));
    await References.destroy({
      where: {
        id: req.params.id,
      },
    });
    res.json({
      msg: "References was deleted!",
    });
  } catch (err) {
    console.log({ msg: err.message });
  }
};
