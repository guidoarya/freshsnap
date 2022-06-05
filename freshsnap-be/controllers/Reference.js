import References from "../models/referenceModel.js";
import { bucket } from "../middlewares/multer.js";
import streamifier from "streamifier";

export const getReference = async (req, res) => {
  try {
    const reference = await References.findAll({
      attributes: ["id", "reference_name", "name", "image"],
    });
    res.json(reference);
  } catch (error) {
    res.status(500).send(`Error, ${error}`);
  }
};

export const addReference = async (req, res) => {
  const { reference_name, name } = req.body;

  console.log(req.file);

  if (!req.file) {
    return res.status(404).send("Input image is not found!");
  }

  if (!name || !reference_name) {
    return res.status(404).send("All field must be filled!");
  }

  const blob = bucket.file(req.file.originalname.replace("", `freshsnap-${Date.now()}`));
  const blobStream = blob.createWriteStream();

  const upload = `https://storage.googleapis.com/${bucket.name}/${blob.name}`;
  try {
    await References.create({
      reference_name: reference_name,
      name: name,
      image: upload,
    });
    return new Promise((resolve, reject) => {
      streamifier
        .createReadStream(req.file.buffer)
        .on("error", (err) => {
          return reject(err);
        })
        .pipe(blobStream)
        .on("finish", (resp) => {
          res.status(201).send("New reference has been created!");
        });
    });
  } catch (error) {
    res.status(500).send(`Error, ${error}`);
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

  const imageName = findReference.image.substring(findReference.image.indexOf("e/") + 2);
  try {
    const file = bucket.file(`${imageName}`);
    file.delete();

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
