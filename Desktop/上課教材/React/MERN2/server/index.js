
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const app = express();
const port = process.env.PORT || 5000;

const URL ="mongodb+srv://jennifer:jennifer123@cluster0.48gav.mongodb.net/<dbname>?retryWrites=true&w=majority"
const OrdersModel = require('./models/patients');
mongoose.connect(
    URL,{
    useNewUrlParser:true,
});

app.use(express.json());
app.use(cors());


app.get("/",async (req, res) => {
    res.send('Hello ! This is Orders API')
});
app.post("/insert",async (req, res) => {
    
    const Message = req.body.Message;
    const orderId = req.body.OrderId;

    const order = new OrdersModel({Message: Message,OrderId : orderId })
    
    try{
        await order.save();
        res.send("insert data");

    }catch(err){
        console.log(err)
    }
});
app.post("/update",async (req, res) => {
    
    const newMessage = req.body.Message;
    const id = req.body.id;

   
    try{
    await OrdersModel.findById(id ,(err, updateMessage) => {
        updateMessage.Message = newMessage;
        updateMessage.save();
    })

    }catch(err){
        console.log(err)
    }
});

app.get("/read",async (req, res) => {
   //patientsModel.find({$where: {patientName:"Apple"}})
   OrdersModel.find({},(err, result) => {
       if(err){
           res.send(err);
       }
       res.send(result);
   })
});

app.get("/read/orderId/:id",async (req, res) => {
    
    const id = req.params.id;
    OrdersModel.find({
        OrderId : req.params.id
    })
        .then(order => res.json(order));
    // await patientsModel.find({},{$where: {OrderId:parseInt(eq.params.id)}},(err,result)=>{
    //     if(err){
    //         res.send(err);
    //     }
        
    //     res.send(result);
    // });
    
 });

app.delete("/delete/:id",async (req, res) => {
   const id = req.params.id;
   await   OrdersModel.findByIdAndRemove(id).exec();
   res.send("delete"+id);
 });
 
app.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
});