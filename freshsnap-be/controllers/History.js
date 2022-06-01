import History from "../models/historyModel.js";

export const getHistory = async (req, res) => {
  try {
    const history = await History.findAll({
      attributes: ["id", "item_name", "user_id"],
    });
    res.json(history);
  } catch (error) {
    console.log(error);
  }
};

export const addHistory = async (req, res) => {
  const { item_name, user_id } = req.body;

  if (!item_name || !user_id) {
    return res.send("All field must be filled!");
  }

  try {
    await Items.create({
      item_name: item_name,
      user_id: user_id,
    });
    return res.json({ msg: "New history has been created!" });
  } catch (error) {
    console.log(error);
    return res.json({ msg: `Error, ${error}` });
  }
};
