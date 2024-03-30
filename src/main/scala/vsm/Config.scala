package vsm

case class ConfigSets(
    invertedIndex: Boolean, 
    individualComparisonVector: Boolean
)

class Config {
    private var invertedIndex = true
    private var individualComparisonVector = false

    
    /* default setting */
    def default() = ConfigSets(true, false)

    def defaultIndividualComparison() = ConfigSets(false, true)
    
    def setInvertedIndex(op: Boolean) = invertedIndex = op
    
    def setIndividualComparisonVector(op: Boolean) = individualComparisonVector = op
    
    def get(): ConfigSets = ConfigSets(invertedIndex, individualComparisonVector)
}