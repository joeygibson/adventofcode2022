"
Please describe the package using the class comment of the included manifest class. The manifest class also includes other additional metadata for the package. These meta data are used by other tools such as the SmalllintManifestChecker and the critics Browser
"
Class {
	#name : 'ManifestAoC2022Day11',
	#superclass : 'PackageManifest',
	#category : 'AoC2022-Day11-Manifest',
	#package : 'AoC2022-Day11',
	#tag : 'Manifest'
}

{ #category : 'code-critics' }
ManifestAoC2022Day11 class >> ruleReCyclomaticComplexityRuleV1FalsePositive [

	<ignoreForCoverage>
	^ #(#(#(#RGMethodDefinition #(#'Monkey class' #from: #true)) #'2025-08-22T11:58:09.816545-04:00') )
]
