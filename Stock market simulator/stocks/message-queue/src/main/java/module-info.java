module messagequeue {
    exports nl.rug.aoop.messagequeue.queues;
    exports nl.rug.aoop.messagequeue.producer;
    exports nl.rug.aoop.messagequeue.consumer;
    exports nl.rug.aoop.messagequeue.message;
    requires static lombok;
    requires org.slf4j;
    requires com.google.gson;
    requires networking;
    requires command;
    requires org.mockito;
}