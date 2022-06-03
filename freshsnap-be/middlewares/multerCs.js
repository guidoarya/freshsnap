// import { getItem, addItem, deleteItem } from "../controllers/Item.js";
// import express from "express";
// const router = express.Router();
// import path from "path";

// // npm streamifier
// import streamifier from "streamifier";
// // npm multer
// import Multer from "multer";

// const multer = Multer({
//   storage: Multer.memoryStorage(),
//   fileSize: 5 * 1024 * 1024,
// });

// // Google Cloud Storage GCS
// import { Storage } from "@google-cloud/storage";
// import { uploadSingle } from "./multer.js";
// const storage = new Storage({
//   projectId: "winter-quanta-351315",
//   keyFilename: "google-cloud-key.json",
// });
// export const bucket = storage.bucket("freshsnap-image");

// router.post("/item", multer.single("image"), function (req, res) {
//   // grab original file name out of multer obj
//   console.log(req.file.originalname);
//   const blob = bucket.file(req.file.originalname.replace("", `freshsnap-${Date.now()}`));
//   // create the GCS stream handler
//   const blobStream = blob.createWriteStream();
//   // Yuck...
//   return new Promise((resolve, reject) => {
//     streamifier
//       .createReadStream(req.file.buffer)
//       .on("error", (err) => {
//         return reject(err);
//       })
//       .pipe(blobStream)
//       .on("finish", (resp) => {
//         res.send("done");
//       });
//   });
// });

// export default router;
