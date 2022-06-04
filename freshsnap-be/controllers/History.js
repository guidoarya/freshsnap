import History from "../models/historyModel.js";

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

  if (!item_name || !user_id) {
    return res.status(404).send("All field must be filled!");
  }

  try {
    await History.create({
      item_name: item_name,
      user_id: user_id,
      location: location,
    });
    return res.status(201).json({ msg: "New history has been created!" });
  } catch (error) {
    console.log(error);
    return res.status(500).json({ msg: `Error, ${error}` });
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

  try {
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
