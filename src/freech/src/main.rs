struct Node {
    value: i64,
    child: Option<Box<FreechTree>>,
}

impl Node {
    fn new() -> Self {
        Node {
            value: 0,
            child: None,
        }
    }
}

struct NodeMedium {
    value: i64,
    left: Option<Box<FreechTree>>,
    right: Option<Box<FreechTree>>,
}

impl NodeMedium {
    fn new(value: i64) -> Self {
        NodeMedium {
            value,
            left: None,
            right: None,
        }
    }
}

struct FreechTree {
    low: Option<Node>,
    medium: Option<NodeMedium>,
    high: Option<Node>,
    is_full: bool,
    length: u8,
}

impl FreechTree {
    fn new() -> Self {
        FreechTree {
            low: None,
            medium: None,
            high: None,
            is_full: false,
            length: 0,
        }
    }

    fn new_medium(value: i64) -> Self {
        FreechTree {
            low: None,
            medium: Some(NodeMedium::new(value)),
            high: None,
            is_full: false,
            length: 0,
        }
    }

    fn is_less_medium(&self, value: i64) -> bool {
        self.medium.as_ref().map_or(false, |m| value < m.value)
    }

    fn is_high(&self, value: i64) -> bool {
        self.high.as_ref().map_or(false, |h| value > h.value)
    }

    fn is_less_low(&self, value: i64) -> bool {
        self.low.as_ref().map_or(false, |l| value < l.value)
    }

    fn split_node(&mut self, value: i64) {
        // Check if it is lower than low.

        /*
            low: 15
            medium: 20,
            value: 10

            The value in this case is lower than medium and low.

            low.child = value/10
        */
        if self.is_less_medium(value) && self.is_less_low(value) {
            let new_node = Node::new();
            let _ = self.low.get_or_insert(new_node);

            self.low.as_mut().unwrap().child = Some(Box::new(FreechTree::new_medium(value)));
        }

        if let Some(medium) = self.medium.take() {
            if value > medium.value {
                let low = self.low.take().unwrap();

                let left = Box::new(FreechTree::new_medium(low.value));
                let right = Box::new(FreechTree::new_medium(value));

                self.medium = Some(NodeMedium {
                    value: medium.value,
                    left: Some(left),
                    right: Some(right),
                });
            }

            if value < medium.value {
                let low = self.low.take().unwrap();

                if value < low.value {
                    let left = Box::new(FreechTree::new_medium(value));
                    let right = Box::new(FreechTree::new_medium(medium.value));

                    self.medium = Some(NodeMedium {
                        value: low.value,
                        left: Some(left),
                        right: Some(right),
                    });
                }

                if value > low.value && value < medium.value {
                    let left = Box::new(FreechTree::new_medium(low.value));
                    let right = Box::new(FreechTree::new_medium(medium.value));

                    self.medium = Some(NodeMedium {
                        value,
                        left: Some(left),
                        right: Some(right),
                    });
                }

                self.low = Some(Node {
                    value: 0,
                    child: Some(Box::new(FreechTree::new())),
                });
            }
        }
    }

    fn swap(&mut self, value: i64) {
        if let Some(medium) = self.medium.take() {
            // medium: 10 value: 15
            if value > medium.value {
                let low = Some(Node {
                    value: medium.value,
                    child: Some(Box::new(FreechTree::new())),
                });

                let new_medium = Some(NodeMedium {
                    value,
                    left: medium.left,
                    right: medium.right
                });

                self.low = low;
                self.medium = new_medium;
            } else if medium.value > value {
                // medium: 15 value: 10
                if let Some(low) = self.low.take() {
                    if value < low.value {
                        self.low = Some(Node {
                            value,
                            child: low.child
                        });

                        self.medium = Some( NodeMedium {
                            value: low.value,
                            left: medium.left,
                            right: medium.right
                        });

                        if let Some(high) = self.high.take() {
                            self.high = Some(Node {
                                value: medium.value,
                                child: high.child
                            });
                        } else {
                            self.high = Some(Node {
                                value: medium.value,
                                child: Some(Box::new(FreechTree::new()))
                            }); 
                        }
                            

                        // let mut new_fretch = FreechTree {
                        //     low: new_low,
                        //     medium: new_medium,
                        //     high,
                        //     is_full: true,
                        //     length: self.length + 1
                        // };
                    } else {
                        println!("POWWW")
                    }
                } 
            }
        }
    }

    // fn insert_recursive(&mut self, value: i64) -> bool {}

    fn insert(&mut self, value: i64) {

        match self.length {
            0 => {
                let new_medium_node: NodeMedium = NodeMedium::new(value);
                let _ = self.medium.get_or_insert(new_medium_node);
                self.length += 1
            },

            1 | 2 => {
                self.swap(value);
                self.length += 1
            },

            3 => {
                self.split_node(value);

                self.length = 1
            },

            _ => println!("NONE"),
        }
    }

    fn print(&mut self) {
        println!("M: {}", self.medium.get_or_insert(*Box::new(NodeMedium::new(5))).value);
        println!("L: {}", self.low.get_or_insert(*Box::new(Node::new())).value);
        println!("H: {}", self.high.get_or_insert(*Box::new(Node::new())).value)
    }
}

fn main() {
    let mut a = FreechTree::new();

    a.insert(10);
    a.insert(15);
    a.insert(30);
    a.insert(31);
    a.print();

    println!("Hello, world!");
}
