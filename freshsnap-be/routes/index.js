import express from "express";
import { getItem, addItem, deleteItem } from "../controllers/Item.js";

const router = express.Router();

// Item
router.get("/item", getItem);
router.post("/item", addItem);
router.delete("/item/:id", deleteItem);

export default router;
