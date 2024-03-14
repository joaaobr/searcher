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

    /* If the left and right of the medium are filled, it will be true. */
    is_populated: bool
}

impl FreechTree {
    fn new() -> Self {
        FreechTree {
            low: None,
            medium: None,
            high: None,
            length: 0,
            is_populated: false
        }
    }

    fn new_medium(value: i64) -> Self {
        FreechTree {
            low: None,
            medium: Some(NodeMedium::new(value)),
            high: None,
            length: 1,
            is_populated: false
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
        /*
        The value in this case is lower than medium and low.

        example:
            low: 15, medium: 20, value: 10


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
            self.is_populated = true
        }
    }

    fn insert_into_high(&mut self, value: i64) {
        self.high = Some(Node {
            value,
            child: None
        })
    }

    /*
        If the value is greater than the current NodeMedium, it makes an exchange with the medium.
    */
    fn swap_value_to_medium(&mut self, medium: NodeMedium, value: i64) {
        let new_low = Some(Node {
            value: medium.value,
            child: None,
        });

        let new_medium = Some(NodeMedium {
            value,
            left: None,
            right: None,
        });

        self.low = new_low;
        self.medium = new_medium;
    }

    fn swap_medium_to_high(&mut self, medium: NodeMedium, value: i64) {
        let new_high = Some(Node {
            value: medium.value,
            child: medium.right,
        });

        let new_medium = Some(NodeMedium {
            value,
            left: medium.left,
            right: None,
        });

        self.medium = new_medium;
        self.high = new_high;
    }


    /*
        When the value is less than low, it makes an exchange between the value and low.

        case:
            low -> 10, medium -> 15, value -> 5
        return this:
            low -> 5, medium -> 10, high -> 15,
    */
    fn swap_value_to_low(&mut self, medium: NodeMedium, low: Node, value: i64) {
        self.low = Some(Node {
            value,
            child: low.child
        });

        self.medium = Some( NodeMedium {
            value: low.value,
            left: medium.left,
            right: medium.right
        });

        self.high = Some(Node {
            value: medium.value,
            child: None
        });
    }

    fn swap(&mut self, value: i64) {
        if let Some(medium) = self.medium.take() {
            if value > medium.value  && self.length == 2  {
                self.insert_into_high(value);
                self.medium = Some(medium);
            } else if value > medium.value {
                self.swap_value_to_medium(medium, value)
            } else if medium.value > value {

                if let Some(low) = self.low.take() {
                    if value < low.value {
                        self.swap_value_to_low(medium, low, value)             
                    } else {
                        self.swap_medium_to_high(medium, value)
                    }
                } 
            }
        }
    }

    fn insert_into(&mut self, value: i64) {
        match self.length {
            0 => {
                self.medium = Some(NodeMedium::new(value));
                self.length += 1
            },

            1 | 2 => {
                self.swap(value);
                self.length += 1
            },

            3 => {
                self.split_node(value);
                self.length = 1;
                self.is_populated = true
            },

            _ => panic!("ERROR: length is inconpatible."),
        }
    }

    fn insert_recursive(&mut self, value: i64, _previous: &mut FreechTree) {
        match self.is_populated {
            true => {
                if let Some(mut medium) = self.medium.take() {
                    if value > medium.value {
                        medium.right.as_mut().unwrap().insert_recursive(value, self);
                    } else if value < medium.value {
                        medium.left.as_mut().unwrap().insert_recursive(value, self);
                    }

                    self.medium = Some(medium);
                } else {
                    panic!("...")
                }
            },
            false => {
                self.insert_into(value)
            }
        }
    }

    fn insert(&mut self, value: i64) {
        self.insert_recursive(value, &mut FreechTree::new())
    }

    fn print(&mut self) {
        println!("{}", self.medium.get_or_insert(*Box::new(NodeMedium::new(-5))).value);
    }
}

fn main() {
    let mut freech_tree = FreechTree::new();

    freech_tree.insert(10);
    freech_tree.insert(15);
    freech_tree.insert(20);
    freech_tree.insert(30);
    freech_tree.insert(35);
    freech_tree.insert(40);

    print!("MEDIUM: ");
    // pirnt current medium
    freech_tree.print();

    print!("LEFT MEDIUM: ");

    // print left medium
    freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(-5))).left.get_or_insert(Box::new(*Box::new(FreechTree::new()))).print();
    // print low left medium
    
    println!("LEFT LOWW: {}", freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(-5))).left.get_or_insert(Box::new(*Box::new(FreechTree::new()))).low
    .get_or_insert(*Box::new(Node::new())).value);

    print!("RIGHT MEDIUM: ");

    // print right medium
    freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(-5))).right.get_or_insert(Box::new(*Box::new(FreechTree::new()))).print();

    println!("RIGHT LOWW: {}", freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(-5))).right.get_or_insert(Box::new(*Box::new(FreechTree::new()))).low
    .get_or_insert(*Box::new(Node::new())).value);

    println!("RIGHT HIGH: {}", freech_tree.medium.get_or_insert(*Box::new(NodeMedium::new(-5))).right.get_or_insert(Box::new(*Box::new(FreechTree::new()))).high
    .get_or_insert(*Box::new(Node::new())).value);
}
