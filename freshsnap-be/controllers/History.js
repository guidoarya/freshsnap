import History from "../models/historyModel.js";
import { bucket } from "../middlewares/multer.js";
import streamifier from "streamifier";

export const getHistory = async (req, res) => {
  try {
    const history = await History.findAll({
      attributes: ["id", "user_name", "location", "item_name", "createdAt", "image"],
    });
    res.status(200).json(history);
  } catch (error) {
    return res.status(500).json({ msg: `Error, ${error}` });
  }
};

export const addHistory = async (req, res) => {
  const { user_name, item_name, location } = req.body;

  if (!req.file) {
    return res.status(404).send("Image field must be filled!");
  }

  if (!user_name || !location || !item_name) {
    return res.status(404).send("All field must be filled!");
  }

  const blob = bucket.file(req.file.originalname.replace("", `freshsnap-${Date.now()}`));
  const blobStream = blob.createWriteStream();
  try {
    const upload = `https://storage.googleapis.com/${bucket.name}/${blob.name}`;
    await History.create({
      user_name: user_name,
      item_name: item_name,
      location: location,
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
          res.status(201).send("New history has been created!");
        });
    });
  } catch (error) {
    res.status(500).send(`Error, ${error}`);
  }
};

export const deleteHistory = async (req, res) => {
  const findHistory = await History.findOne({
    where: {
      id: req.params.id,
    },
  });

  if (!findHistory) {
    return res.status(404).send("History is not found!");
  }
  const imageName = findItem.image.substring(findItem.image.indexOf("e/") + 2);

  try {
    const file = bucket.file(`${imageName}`);
    file.delete();
    await History.destroy({
      where: {
        id: req.params.id,
      },
    });

    res.status(200).send("History was deleted!");
  } catch (err) {
    res.status(500).send(`Error, ${error}`);
  }
};
