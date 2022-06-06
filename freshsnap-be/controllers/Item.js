import Items from "../models/itemModel.js";
import { bucket } from "../middlewares/multer.js";
import streamifier from "streamifier";

export const getItem = async (req, res) => {
  let fresh = "freshbanana";
  let rotten = "rottenbanana";

  console.log(fresh.substring(fresh.indexOf("h") + 1));
  console.log(rotten.substring(rotten.indexOf("n") + 1));

  try {
    const item = await Items.findAll({
      attributes: ["id", "name", "type", "image", "howtokeep"],
    });
    res.status(200).json(item);
  } catch (error) {
    res.status(500).send(`Error, ${error}`);
  }
};

export const addItem = async (req, res) => {
  const { name, type, howtokeep } = req.body;

  if (!req.file) {
    return res.status(404).send("Image field must be filled!");
  }

  if (!name || !type || !howtokeep) {
    return res.status(404).send("All field must be filled!");
  }

  const blob = bucket.file(req.file.originalname.replace("", `freshsnap-${Date.now()}`));
  const blobStream = blob.createWriteStream();
  try {
    const upload = `https://storage.googleapis.com/${bucket.name}/${blob.name}`;
    await Items.create({
      name: name,
      type: type,
      image: upload,
      howtokeep: howtokeep,
    });
    return new Promise((resolve, reject) => {
      streamifier
        .createReadStream(req.file.buffer)
        .on("error", (err) => {
          return reject(err);
        })
        .pipe(blobStream)
        .on("finish", (resp) => {
          res.status(201).send("New item has been created!");
        });
    });
  } catch (error) {
    res.status(500).send(`Error, ${error}`);
  }
};

export const deleteItem = async (req, res) => {
  const findItem = await Items.findOne({
    where: {
      id: req.params.id,
    },
  });

  if (!findItem) {
    return res.status(404).send("Item is not found!");
  }

  const imageName = findItem.image.substring(findItem.image.indexOf("e/") + 2);

  try {
    const file = bucket.file(`${imageName}`);
    file.delete();

    await Items.destroy({
      where: {
        id: req.params.id,
      },
    });

    res.status(200).send("Item was deleted!");
  } catch (err) {
    res.status(500).send(`Error, ${error}`);
  }
};
