"
Please describe the package using the class comment of the included manifest class. The manifest class also includes other additional metadata for the package. These meta data are used by other tools such as the SmalllintManifestChecker and the critics Browser
"
Class {
	#name : 'ManifestAoC2022',
	#superclass : 'PackageManifest',
	#category : 'AoC2022-Manifest',
	#package : 'AoC2022',
	#tag : 'Manifest'
}

{ #category : 'code-critics' }
ManifestAoC2022 class >> ruleCollectionProtocolRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#AoCDay15 #createMap: #false)) #'2025-08-28T23:20:43.737865-04:00') )
]

{ #category : 'code-critics' }
ManifestAoC2022 class >> ruleEmptyExceptionHandlerRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#AoCDay14 #part1: #false)) #'2025-08-27T21:15:58.683933-04:00') )
]

{ #category : 'code-critics' }
ManifestAoC2022 class >> rulePrecedenceRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#AoCDay05 #part1: #false)) #'2025-08-18T22:47:39.516776-04:00') )
]

{ #category : 'code-critics' }
ManifestAoC2022 class >> ruleReCyclomaticComplexityRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#AoCDay14 #part1: #false)) #'2025-08-27T21:32:30.75687-04:00') )
]

{ #category : 'code-critics' }
ManifestAoC2022 class >> ruleTempVarOverridesInstVarRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#AoCDay12 #part2: #false)) #'2025-08-22T15:55:32.409682-04:00') )
]

{ #category : 'code-critics' }
ManifestAoC2022 class >> ruleToDoWithIncrementRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#AoCDay10 #part1: #false)) #'2025-08-21T22:35:28.15258-04:00') )
]
