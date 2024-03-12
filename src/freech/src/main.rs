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
    length: u8,
}

impl FreechTree {
    fn new() -> Self {
        FreechTree {
            low: None,
            medium: None,
            high: None,
            length: 0,
        }
    }

    fn new_medium(value: i64) -> Self {
        FreechTree {
            low: None,
            medium: Some(NodeMedium::new(value)),
            high: None,
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
        // println!("LOWWW: {}", self.low.get_or_insert(*Box::new(Node::new())).value);
        // println!("MEIUMM: {}", self.medium.get_or_insert(*Box::new(NodeMedium::new(-25))).value);
        // println!("HIGHH: {}", self.high.get_or_insert(*Box::new(Node::new())).value);

        /*
            low: 15
            medium: 20,
            value: 10

            The value in this case is lower than medium and low.

            low.child = value/10

            FIX THIS CODE!
        */
        if self.is_less_medium(value) && self.is_less_low(value) {
            let new_node = Node::new();
            let _ = self.low.get_or_insert(new_node);

            self.low.as_mut().unwrap().child = Some(Box::new(FreechTree::new_medium(value)));
        }

        if let Some(medium) = self.medium.take() {
            if value > medium.value && self.is_high(value) {
                let high = self.high.take().unwrap();
                let low = self.low.take().unwrap();

                let mut left = Box::new(FreechTree::new_medium(medium.value));
                left.low = Some(Node { value: low.value, child: None });

                let right = Box::new(FreechTree::new_medium(value));
                
                self.medium = Some(NodeMedium {
                    value: high.value,
                    left: Some(left),
                    right: Some(right),
                });
            } else if value > medium.value {
                let low = self.low.take().unwrap();

                let left = Box::new(FreechTree::new_medium(low.value));
                let right = Box::new(FreechTree::new_medium(value));

                self.medium = Some(NodeMedium {
                    value: medium.value,
                    left: Some(left),
                    right: Some(right),
                });
                
            } else if value < medium.value {
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

                self.low = None;
                self.high = None;
            }
        }
    }

    fn swap(&mut self, value: i64) {
        if let Some(medium) = self.medium.take() {

            if value > medium.value  && self.length == 2  {
                let new_high = Some(Node {
                    value,
                    child: None,
                });

                // self.low = low;
                self.high = new_high;
                self.medium = Some(medium);
            } else if value > medium.value{
                // medium: 10 value: 15
                let new_low = Some(Node {
                    value: medium.value,
                    child: None,
                });

                let new_medium = Some(NodeMedium {
                    value,
                    left: None,
                    right: None,
                });

                // self.low = low;
                self.low = new_low;
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
        println!("{}", self.medium.get_or_insert(*Box::new(NodeMedium::new(5))).value);
    }
}

fn main() {
    let mut freech_tree = FreechTree::new();

    freech_tree.insert(10);
    freech_tree.insert(15);
    freech_tree.insert(30);
    freech_tree.insert(31);
    // pirnt current medium
    freech_tree.print();

    // print left medium
    freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(5))).left.get_or_insert(Box::new(*Box::new(FreechTree::new()))).print();
    // print low left medium
    
    println!("LEFT LOWW: {}", freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(5))).left.get_or_insert(Box::new(*Box::new(FreechTree::new()))).low
    .get_or_insert(*Box::new(Node::new())).value);

    // print right medium
    freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(5))).right.get_or_insert(Box::new(*Box::new(FreechTree::new()))).print();

}
