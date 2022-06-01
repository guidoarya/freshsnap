import express from "express";
import { getItem, addItem, deleteItem } from "../controllers/Item.js";
import { getUser, Register, updateUser, Login, Logout, getSpecifyUser, deleteUser } from "../controllers/User.js";
import uploadSingle from "../middlewares/multer.js";
import { detail, historyPage, homePage } from "../controllers/apiControllers.js";
import { addReference, deleteReference, getReference } from "../controllers/Reference.js";
import Auth from "../middlewares/auth.js";
import { addHistory, getHistory } from "../controllers/History.js";

const router = express.Router();

// Item
router.get("/item", getItem);
router.post("/item", uploadSingle, addItem);
router.delete("/item/:id", deleteItem);

// User
router.get("/user", getUser);
router.delete("/user", deleteUser);
router.get("/user/:id", Auth.verifyTokenUser, getSpecifyUser);
router.post("/user", uploadSingle, Register);
router.patch("/user/:id", uploadSingle, updateUser);
router.post("/login", uploadSingle, Login);
router.delete("/logout", Logout);

// Reference
router.get("/reference", getReference);
router.post("/reference", uploadSingle, addReference);
router.delete("/reference/:id", deleteReference);

// History
router.get("/history", getHistory);
router.post("/history", addHistory);

// API Routes
router.get("/home-page", homePage);
router.get("/detail/:id", detail);
router.get("/history-page", historyPage);

export default router;
