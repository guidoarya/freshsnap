import express from "express";
import dotenv from "dotenv";
import cookieParser from "cookie-parser";
import db from "./config/database.js";
import router from "./routes/index.js";
// import router from "./middlewares/multerCs.js";
import cors from "cors";
import bodyParser from "body-parser";
import multer from "multer";
import path from "path";
import { fileURLToPath } from "url";

dotenv.config();
const app = express();

app.use(express.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
app.use(express.static(path.join(__dirname, "public")));

try {
  await db.authenticate();
  console.log("Database connected");
} catch (err) {
  console.log(err);
}

app.use(
  cors({
    credentials: true,
    origin: "http://localhost:3000",
  })
);

// const upload = multer({ storage: diskStorage, fileFilter: imgFilter, limits: { fieldSize: 10 * 1024 * 1024 } });

// app.use(upload.single("dummy_picture", "profilePict"));

app.get("/", async (req, res) => {
  try {
    res.send(`Welcome to API Page for Kasbaik Backend for Users `);
  } catch (error) {
    console.log(error);
  }
});

app.use(cookieParser());
app.use(express.json());

app.use(router);

app.listen(5000, () => {
  console.log("Server running at port: 5000");
});
