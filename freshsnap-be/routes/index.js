import express from "express";
import { getItem, addItem, deleteItem } from "../controllers/Item.js";
import { getUser, Register, updateUser, Login, Logout, getSpecifyUser, deleteUser } from "../controllers/User.js";
import { uploadGoogleStorage, uploadSingle } from "../middlewares/multer.js";
import { detail, historyPage, homePage } from "../controllers/apiControllers.js";
import { addReference, deleteReference, getReference } from "../controllers/Reference.js";
import Auth from "../middlewares/auth.js";
import { addHistory, deleteHistory, getHistory } from "../controllers/History.js";

const router = express.Router();

// Item
router.get("/item", getItem);
router.post("/item", uploadGoogleStorage.single("image"), addItem);
router.delete("/item/:id", deleteItem);

// User
router.get("/user", Auth.verifyTokenUser, getUser);
router.delete("/user/:id", deleteUser);
router.get("/user/:id", Auth.verifyTokenUser, getSpecifyUser);
router.post("/user", uploadSingle, Register);
router.patch("/user/:id", uploadSingle, updateUser);

router.post("/login", uploadSingle, Login);
router.delete("/logout", Logout);

// Reference
router.get("/reference", getReference);
router.post("/reference", uploadGoogleStorage.single("image"), addReference);
router.delete("/reference/:id", deleteReference);

// History
router.get("/history", Auth.verifyTokenUser, getHistory);
router.post("/history", Auth.verifyTokenUser, uploadGoogleStorage.single("image"), addHistory);
router.post("/history/:id", Auth.verifyTokenUser, deleteHistory);

// API Routes
router.get("/home-page", homePage);
router.get("/detail/:id", detail);
router.get("/history-page", Auth.verifyTokenUser, historyPage);

export default router;
