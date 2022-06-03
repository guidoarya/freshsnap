import History from "../models/historyModel.js";
import Items from "../models/itemModel.js";

export const getHistory = async (req, res) => {
  try {
    const history = await History.findAll({
      attributes: ["id", "item_name", "user_id", "location"],
    });
    res.status(200).json(history);
  } catch (error) {
    return res.status(500).json({ msg: `Error, ${error}` });
  }
};

export const addHistory = async (req, res) => {
  const { item_name, user_id, location } = req.body;

  console.log(req.body);

  if (!item_name || !user_id) {
    return res.status(404).send("All field must be filled!");
  }

  try {
    await History.create({
      item_name: item_name,
      user_id: user_id,
      location: location,
    });
    return res.json({ msg: "New history has been created!" });
  } catch (error) {
    console.log(error);
    return res.status(500).json({ msg: `Error, ${error}` });
  }
};
