const mongoose = require('mongoose');



const OrdersSchema = new mongoose.Schema({
    Message:{
        type:String,
        required: true,

    },
    OrderId:{
        type: Number,
        required:true,
    }

});

const Orders = mongoose.model('Orders', OrdersSchema);

module.exports =  Orders;