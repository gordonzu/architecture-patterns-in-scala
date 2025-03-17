package com.gordonyx.model

import java.time.LocalDate
import scala.collection.mutable

case class OrderLine(orderid: String, sku: String, qty: Int)

class Batch(ref: String, sku: String, qty: Int, eta: Option[LocalDate]): 
    var reference:          String = ref 
    var batchSku:           String = sku 
    var batchEta:           Option[LocalDate] = eta 
    var availableQty:       Int = qty 
    var allocations:        mutable.Set[OrderLine] = mutable.Set()

    def canAllocate(line: OrderLine): Boolean = {
        this.sku == line.sku && this.availableQty >= line.qty
    }

def allocate(batch: Batch, line: OrderLine): Unit = 
    batch.availableQty -= line.qty



