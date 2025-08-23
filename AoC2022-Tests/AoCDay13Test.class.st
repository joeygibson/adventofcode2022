Class {
	#name : 'AoCDay13Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay13Test >> setUp [
	super setUp.
	self day: '13'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay13 new.
]

{ #category : 'tests' }
AoCDay13Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 5529.
]

{ #category : 'tests' }
AoCDay13Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 13.
]
