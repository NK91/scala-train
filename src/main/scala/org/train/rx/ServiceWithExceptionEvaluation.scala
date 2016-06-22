package org.train.rx

import org.train.rx.extention.{Evaluation, ServiceException, SomeDate}

/**
  * Created by kisilnazar on 22.06.16.
  */
trait ServiceWithExceptionEvaluation extends Evaluation {
  override def someEvaluating(): SomeDate = {
    throw ServiceException("Some evaluation exception")
  }
}
