import History from "../models/historyModel.js";
import Items from "../models/itemModel.js";
import References from "../models/referenceModel.js";

export const homePage = async (req, res) => {
  try {
    const Fruits = await Items.findAll({
      where: {
        type: "Fruit",
      },
    });

    const Vegetables = await Items.findAll({
      where: {
        type: "Vegetable",
      },
    });

    res.status(200).json({
      Fruits,
      Vegetables,
    });
  } catch (error) {
    console.log(error);
    res.status(500).json({ message: "Internal Server Error" });
  }
};

export const detail = async (req, res) => {
  try {
    if (!req.params.id) {
      return res.status(400).json({ msg: "Id parameter must be sent" });
    }

    const Detail = await Items.findAll({
      where: {
        id: req.params.id,
      },
    });

    const Reference = await References.findAll({
      where: {
        name: Detail[0].name,
      },
    });

    res.status(200).json({
      Detail,
      Reference,
    });
  } catch (error) {
    res.status(500).json({ message: "Internal Server Error" });
  }
};

export const historyPage = async (req, res) => {
  try {
    const HistoryDetail = await History.findAll({
      attributes: ["id", "user_name", "item_name", "location", "image", "createdAt"],
    });

    res.status(200).json({
      HistoryDetail,
    });
  } catch (error) {
    res.status(500).send(`Error, ${error}`);
  }
};
