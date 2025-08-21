Class {
	#name : 'AoCDay09Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay09Test >> setUp [
	super setUp.
	self day: '9'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay09 new.
]

{ #category : 'tests' }
AoCDay09Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 6391.
]

{ #category : 'tests' }
AoCDay09Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 13.
]
