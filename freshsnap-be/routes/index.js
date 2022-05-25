import express from "express";
import { getItem, addItem, deleteItem } from "../controllers/Item.js";
import { getUser, Register, updateUser, Login, Logout } from "../controllers/User.js";
import uploadSingle from "../middlewares/multer.js";
import { detail, homePage } from "../controllers/apiControllers.js";
import { addReference, deleteReference, getReference } from "../controllers/Reference.js";

const router = express.Router();

// Item
router.get("/item", getItem);
router.post("/item", uploadSingle, addItem);
router.delete("/item/:id", deleteItem);

// User
router.get("/user", getUser);
router.post("/user", uploadSingle, Register);
router.patch("/user/:id", uploadSingle, updateUser);
router.post("/login", Login);
router.delete("/logout", Logout);

// Reference
router.get("/reference", getReference);
router.post("/reference", uploadSingle, addReference);
router.delete("/reference/:id", deleteReference);

// API Routes
router.get("/home-page", homePage);
router.get("/detail/:id", detail);

export default router;
