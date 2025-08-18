Class {
	#name : 'AoCDay04Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay04Test >> setUp [
	super setUp.
	self day: '4'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay04 new.
]

{ #category : 'tests' }
AoCDay04Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 569.
]

{ #category : 'tests' }
AoCDay04Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 2.
]
